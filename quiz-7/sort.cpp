#include <stdio.h>
#include <time.h>
#include <vector>
#include <cstdlib>
#include <array>        // std::array
#include "helpers.h"	// functions to print, shuffle, etc.

int merge_count = 0;
int merge_fail_return = 0;

void mergesort(InputArray& array, int start, int end) {
	merge_count ++;
	
	if (start >= end) {
		merge_fail_return++;
		return;
	}

	int mid = start + (end - start) / 2;	//halfway point

	if(start < mid){ // Only when there is more than one element in the first half
		mergesort(array, start, mid);
	}

	if(mid + 1 < end){ // Only when there is more than one element in the second half
		mergesort(array, mid+1, end);
	}
	
	merge(array, start, mid, end);
}


void quicksort(InputArray& array, int start, int end) {
	
    if (start < end) {
		// p is the index of the correctly placed pivot
        int p = partition(array, start, end);	// chooses the first element as the pivot
 
		// sort subarrays on either side of pivot

		quicksort(array, start, p - 1);
        quicksort(array, p + 1, end);
	}
}


int main() {
	srand(time(NULL)); //initialize random seed
	clock_t before;
	double totalMerge = 0;
	double totalQuick = 0;
	
	// starting arrays
	InputArray array = {};
	InputArray array2 = {};
	initializeReverseOrder(array);
	copyArray(array, array2);	// copy initial array to duplicate input
	//printArray(array)
	
	
	// time the first sorting algorithm
	before = clock();
	mergesort(array, 0, array.size()-1);	
	totalMerge = clock() - before;	
	// printArray(array);

	// time the second sorting algorithm
	// before = clock();
	// shuffleArray(array2);
	// quicksort(array2, 0, array2.size()-1);
	// totalQuick = clock() - before;
	//printArray(array2);

	printf("merge count %d\n", merge_count);
	printf("merge fail %d\n", merge_fail_return);
	
	
	// display results
	printf("Total time for mergesort: %.3f seconds\n", totalMerge/CLOCKS_PER_SEC);	
	printf("Total time for quicksort: %.3f seconds\n", totalQuick/CLOCKS_PER_SEC);	
}





