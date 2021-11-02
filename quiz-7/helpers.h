#ifndef HELPERS_H
#define HELPERS_H

typedef std::array<int, 100000> InputArray;	// specify length of array to be sorted here

void shuffleArray(InputArray& array);
void printArray(const InputArray& array);
void initializeRandom(InputArray& array);
void initializeSorted(InputArray& array);
void initializeReverseOrder(InputArray& array);
void copyArray(InputArray& source, InputArray& destination);

// for sorting algorithms
void swap(InputArray& array, int a, int b);
int partition(InputArray& array, int start, int end);
void merge(InputArray& array, int start, int mid, int end);

#endif