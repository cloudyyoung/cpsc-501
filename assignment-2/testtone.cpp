/*
    Produces a single tone, and writes the result in .wav file format to the filename given as user input
    Note that the specified filename should have a .wav ending

    Usage: ./testtone outputFile

    Contains functions for writing .wav files that can be used for the assignment
*/

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <iostream>
#include <math.h>	// includes sin
#include <string>
#include <fstream>
#include "functions.h"

// CONSTANTS ******************************

#define PI              	3.14159265358979

// Frequency of tone to be created (A = 440 Hz)
#define TONE_FREQUENCY		440

// Duration of tone to be created (3 seconds for now)
#define SECONDS				3

//  Standard sample rate in Hz
#define SAMPLE_RATE     	44100.0

//  Standard sample size in bits
#define BITS_PER_SAMPLE		16

// Standard sample size in bytes		
#define BYTES_PER_SAMPLE	(BITS_PER_SAMPLE/8)

// Rescaling factor to convert between 16-bit shorts and doubles between -1 and 1
#define MAX_SHORT_VALUE		32768

// Number of channels
#define MONOPHONIC			1
#define STEREOPHONIC		2

// Offset of the fmt chunk in the WAV header
#define FMT_OFFSET			12

using namespace std;



int main(int argc, char** argv) {
    char* outputFilename;
    char* inputFileName;
    if (argc != 3) {
        printf("Please enter both input and output filenames\n");
        exit(-1);
    }
    inputFileName = argv[1];
    outputFilename = argv[2];



    printf("Reading input array from %s....\n", inputFileName);
    int inputChannels;
    double* inputArray;
    int inputArraySize;

    inputArray = readWavFile(&inputArraySize, &inputChannels, inputFileName);
    printf("%d\n", inputChannels);
    int outputChannels;
    double* outputArray;
    int outputArraySize;

    if (inputChannels == 1) {
        outputChannels = 2;
        outputArray = convertToStereo(inputArray, &outputArraySize, inputArraySize);
    }

    writeWavFile(outputArray, outputArraySize, outputChannels, outputFilename);

    printf("Finished");

}

double* convertToStereo(double* inputArray, int* outputArraySize, int inputArraySize) {
    *outputArraySize = 2 * inputArraySize;
    double* outputArray = new double[*outputArraySize];
    for (int i = 0; i < *outputArraySize - 1; i += 2) {
        outputArray[i] = inputArray[i / 2];
        outputArray[i + 1] = inputArray[i / 2];
    }
    return outputArray;
}

double* readWavFile(int* arraySize, int* channels, char* filename) {
    double* array;

    FILE* inputFileStream = fopen(filename, "rb");
    if (inputFileStream == NULL) {
        printf("File %s could not be opened for reading\n", filename);
        exit(-1);
    }

    int numSamples;

    readWavFileHeader(channels, &numSamples, inputFileStream);

    printf("Channels: %d\n", *channels);
    printf("Number of samples: %d\n", numSamples);

    //check that the number of samples is greater than zero (to avoid problems with arrays)
    if (numSamples <= 0) {
        printf("The file %s doesn't contain any samples. Exiting the program.\n", filename);
        exit(0);
    }

    *arraySize = numSamples * (*channels);

    array = new double[*arraySize];
    short* intArray = new short[*arraySize];

    int count = fread(intArray, BYTES_PER_SAMPLE, numSamples, inputFileStream);

    //  for (int i=0; i<numSamples; i++) {
    //	array[i] = ((double) intArray[i])/ MAX_SHORT_VALUE;
    //  }

    int largest = 0;
    for (int i = 0; i < *arraySize; i++) {
        if (intArray[i] > largest) {
            largest = intArray[i];
        }
    }

    for (int i = 0; i < *arraySize; i++) {
        array[i] = ((double)intArray[i]) / largest;
    }



    //clean up memory
    delete[] intArray;
    //int fclose(FILE *inputFileStream);

    return array;
}


void readWavFileHeader(int* channels, int* numSamples, FILE* inputFile) {
    int sampleRate;
    int bytesPerSecond;
    int dataChunkSize;

    unsigned char buffer[64];
    fread(buffer, sizeof(unsigned char), FMT_OFFSET, inputFile);

    freadIntLSB(inputFile);
    int fmtSize = freadIntLSB(inputFile);
    freadShortLSB(inputFile); //audio fmt = 1

    *channels = freadShortLSB(inputFile);
    sampleRate = freadIntLSB(inputFile);
    bytesPerSecond = freadIntLSB(inputFile);

    int frameSize = freadShortLSB(inputFile);
    int bitRate = freadShortLSB(inputFile);

    //confirm bit rate is 16
    if (bitRate != BITS_PER_SAMPLE) {
        printf("Error: bit rate of provided WAV file is not 16. Exiting.");
        exit(-1);
    }

    //confirm sample rate is 44.1 kHz
    if (sampleRate != SAMPLE_RATE) {
        printf("Error: sample rate of provided WAV file is not 44.1 Hz. Exiting.");
        exit(-1);
    }

    //skip forward to dataChunkSize
    fread(buffer, sizeof(unsigned char), fmtSize - 12, inputFile);

    dataChunkSize = freadIntLSB(inputFile);
    printf("Data chunk size: %d\n", dataChunkSize);

    *numSamples = dataChunkSize / (BYTES_PER_SAMPLE * (*channels));

}



/*
Writes the header for a WAV file with the given attributes to
 the provided filestream
*/

void writeWavFileHeader(int channels, int numberSamples, double outputRate, FILE* outputFile) {
    // Note: channels is not currently used. You will need to add this functionality
    // yourself for the bonus part of the assignment

    /*  Calculate the total number of bytes for the data chunk  */
    int dataChunkSize = numberSamples * BYTES_PER_SAMPLE;
    cout << "subchunk2 size: " << dataChunkSize << endl;

    /*  Calculate the total number of bytes for the form size  */
    int formSize = 36 + dataChunkSize;

    /*  Calculate the total number of bytes per frame  */
    short int frameSize = channels * BYTES_PER_SAMPLE;

    /*  Calculate the byte rate  */
    int bytesPerSecond = (int)ceil(outputRate * frameSize);

    /*  Write header to file  */
    /*  Form container identifier  */
    fputs("RIFF", outputFile);

    /*  Form size  */
    fwriteIntLSB(formSize, outputFile);

    /*  Form container type  */
    fputs("WAVE", outputFile);

    /*  Format chunk identifier (Note: space after 't' needed)  */
    fputs("fmt ", outputFile);

    /*  Format chunk size (fixed at 16 bytes)  */
    fwriteIntLSB(16, outputFile);

    /*  Compression code:  1 = PCM  */
    fwriteShortLSB(1, outputFile);

    /*  Number of channels  */
    fwriteShortLSB((short)channels, outputFile);

    /*  Output Sample Rate  */
    fwriteIntLSB((int)outputRate, outputFile);

    /*  Bytes per second  */
    fwriteIntLSB(bytesPerSecond, outputFile);

    /*  Block alignment (frame size)  */
    fwriteShortLSB(frameSize, outputFile);

    /*  Bits per sample  */
    fwriteShortLSB(BITS_PER_SAMPLE, outputFile);

    /*  Sound Data chunk identifier  */
    fputs("data", outputFile);

    /*  Chunk size  */
    fwriteIntLSB(dataChunkSize, outputFile);
}


/*
Creates a WAV file with the contents of the provided outputArray as the samples, and writes
it to the given filename
 */

void writeWavFile(double* outputArray, int outputArraySize, int channels, char* filename) {
    // Note: channels is not currently used. You will need to add this functionality
    // yourself for the bonus part of the assignment

  //open a binary output file stream for writing
    FILE* outputFileStream = fopen(filename, "wb");
    if (outputFileStream == NULL) {
        printf("File %s cannot be opened for writing\n", filename);
        return;
    }

    //create an 16-bit integer array to hold rescaled samples
    short* intArray = new short[outputArraySize];

    //find the largest entry and uses that to rescale all other
    // doubles to be in the range (-1, 1) to prevent 16-bit integer overflow
    double largestDouble = 1;
    for (int i = 0; i < outputArraySize; i++) {
        if (outputArray[i] > largestDouble) {
            largestDouble = outputArray[i];
        }
    }

    for (int i = 0; i < outputArraySize; i++) {
        intArray[i] = (short)((outputArray[i] / largestDouble) * MAX_SHORT_VALUE);
    }

    int numSamples = outputArraySize;

    // actual file writing
    writeWavFileHeader(channels, numSamples, SAMPLE_RATE, outputFileStream);
    fwrite(intArray, sizeof(short), outputArraySize, outputFileStream);

    //clear memory from heap
    delete[] intArray;
}


//writes an integer to the provided stream in little-endian form
size_t fwriteIntLSB(int data, FILE* stream) {
    unsigned char array[4];

    array[3] = (unsigned char)((data >> 24) & 0xFF);
    array[2] = (unsigned char)((data >> 16) & 0xFF);
    array[1] = (unsigned char)((data >> 8) & 0xFF);
    array[0] = (unsigned char)(data & 0xFF);
    return fwrite(array, sizeof(unsigned char), 4, stream);
}


//reads an integer from the provided stream in little-endian form
int freadIntLSB(FILE* stream) {
    unsigned char array[4];

    fread(array, sizeof(unsigned char), 4, stream);

    int data;
    data = array[0] | (array[1] << 8) | (array[2] << 16) | (array[3] << 24);

    return data;
}


//writes a short integer to the provided stream in little-endian form
size_t fwriteShortLSB(short int data, FILE* stream) {
    unsigned char array[2];

    array[1] = (unsigned char)((data >> 8) & 0xFF);
    array[0] = (unsigned char)(data & 0xFF);
    return fwrite(array, sizeof(unsigned char), 2, stream);
}


//reads a short integer from the provided stream in little-endian form
short int freadShortLSB(FILE* stream) {
    unsigned char array[2];

    fread(array, sizeof(unsigned char), 2, stream);

    int data;
    data = array[0] | (array[1] << 8);

    return data;
}


