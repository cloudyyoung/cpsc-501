double* convertToStereo(double* inputArray, int* outputArraySize, int inputArraySize);
double* readWavFile(int* arraySize, int* channels, char* filename);
void readWavFileHeader(int* channels, int* numSamples, FILE* inputFile);
void writeWavFileHeader(int channels, int numberSamples, double outputRate, FILE* outputFile);
void writeWavFile(double* outputArray, int outputArraySize, int channels, char* filename);
size_t fwriteIntLSB(int data, FILE* stream);
int freadIntLSB(FILE* stream);
size_t fwriteShortLSB(short int data, FILE* stream);
short int freadShortLSB(FILE* stream);
