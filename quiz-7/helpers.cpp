#include <algorithm>    // std::shuffle
#include <array>        // std::array
#include <random>       // std::default_random_engine
#include <chrono>       // std::chrono::system_clock
#include "helpers.h"



void shuffleArray(InputArray& array) {
	unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();
	std::shuffle (array.begin(), array.end(), std::default_random_engine(seed));
}

void printArray(const InputArray& array) {
	printf("Array of length %u: \n[", array.size());
	for (int i=0; i<array.size()-1; i++) {
	  printf("%d, ", array[i]);
	}
	printf("%d]\n", array[array.size()-1]);	
}

void initializeRandom(InputArray& array) {
	// initializes the given array to a random shuffled order
	for (int i=0; i<array.size(); i++) {
		array[i] = i;
	}
	shuffleArray(array);
}

void initializeSorted(InputArray& array) {
	// initializes the given array in sorted order
	for (int i=0; i<array.size(); i++) {
		array[i] = i;
	}
}

void initializeReverseOrder(InputArray& array) {
	// initializes the given array in reverse sorted order
	for (int i=0; i<array.size(); i++) {
		array[i] = array.size() - i - 1;
	}
}

void copyArray(InputArray& source, InputArray& destination) {
	for (int i=0; i<source.size(); i++) {
		destination[i] = source[i];
	}
}


// helper functions for sorting algorithms


void swap(InputArray& array, int a, int b) {
	int temp = array[a];
	array[a] = array[b];
	array[b] = temp;
}

int partition(InputArray& array, int start, int end) {
	int pivot = array[start];    // pivot is the first element
    int i = start;  // index of rightmost smaller element (eventually swapped with pivot)
 
    for (int j = start + 1; j <= end; j++) {
        // smaller elements are collected at indices 1-i (inclusive)
        if (array[j] <= pivot) {
            i++;    // increment index of smaller element
            swap(array, i, j);
        }
    }
	// place the pivot at index i and return this index
    swap(array, i, start);
    return i;
}

void merge(InputArray& array, int start, int mid, int end) {
	// Merges array[start...mid] and array[mid+1...end] in increasing order.

	// subarray lengths
    int subArrayOne = mid - start + 1;
    int subArrayTwo = end - mid;
  
    // Create temp arrays
	std::vector<int> leftArray(subArrayOne, 0);
	std::vector<int> rightArray(subArrayTwo, 0);
  
    // Copy data to temp arrays leftArray[] and rightArray[]
    for (int i = 0; i < subArrayOne; i++)
        leftArray[i] = array[start + i];
    for (int j = 0; j < subArrayTwo; j++)
        rightArray[j] = array[mid + 1 + j];
  
    int indexOfSubArrayOne = 0, // Initial index of first sub-array
        indexOfSubArrayTwo = 0; // Initial index of second sub-array
    int indexOfMergedArray = start; // Initial index of merged array
  
    // Merge the temp arrays back into array[left..right]
    while (indexOfSubArrayOne < subArrayOne && indexOfSubArrayTwo < subArrayTwo) {
        if (leftArray[indexOfSubArrayOne] <= rightArray[indexOfSubArrayTwo]) {
            array[indexOfMergedArray] = leftArray[indexOfSubArrayOne];
            indexOfSubArrayOne++;
        }
        else {
            array[indexOfMergedArray] = rightArray[indexOfSubArrayTwo];
            indexOfSubArrayTwo++;
        }
        indexOfMergedArray++;
    }
    // Copy the remaining elements of
    // left[], if there are any
    while (indexOfSubArrayOne < subArrayOne) {
        array[indexOfMergedArray] = leftArray[indexOfSubArrayOne];
        indexOfSubArrayOne++;
        indexOfMergedArray++;
    }
    // Copy the remaining elements of
    // right[], if there are any
    while (indexOfSubArrayTwo < subArrayTwo) {
        array[indexOfMergedArray] = rightArray[indexOfSubArrayTwo];
        indexOfSubArrayTwo++;
        indexOfMergedArray++;
    }		
}
