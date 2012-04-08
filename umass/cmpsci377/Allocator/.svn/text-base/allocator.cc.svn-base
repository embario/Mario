/*

 Mario Barrenechea
 11/5/09

 Project 3 - The Memory Allocator


 COMPILE: g++ -O2 -DNDEBUG -Wall -shared allocator.cc –o libmymalloc.so libshim.a –ldl

 3.3 Compiling your allocator as part of a program

 If you want to debug your allcoator it is handy to compile it as part
 of a program, rather than as a shared library.

 For instance:

 g++ -Wall -g -o test allocator.cc test.cc libshim.a -ldl

 LESSONS LEARNED:

 1) DO NOT call "new", "delete", "malloc" or "free" in malloc() or free()! It's a recursive call!
 2) DO NOT USE Strings!

 */

#include <fcntl.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/mman.h>
#include <string.h>
#include <new>
#include "allocator.h"
#include <iostream>
#include <math.h>

using namespace std;

//Open /dev/zero only once (globally)
int fd = open("/dev/zero", O_RDWR);

/** Global variables ****/

//Struct for tracking important info when malloc'ing
struct malloc_t {

	short pageIndex;
	int maxBoundary;
	size_t mallocSize;
	void * mallocSpace;
};

//Lists to hold memory chunks
BibopHeader * g_headerList[8];

//And a list that's full
BibopHeader * g_fullList = NULL;

//Extra Function declarations
BibopHeader * createBibopHeader(malloc_t & mallocInfo);
short lookupIndex(size_t size);
bool headerIsFull(BibopHeader * header);
void allocateObject(malloc_t & mallocInfo);
void moveHeaderToHeaderList(BibopHeader * header, short pageIndex);
void moveHeaderToFullList(BibopHeader * header);
BibopHeader * getBibopHeader(void * ptr);

Allocator::Allocator(void) {
}

/** createBibopHeader():

 This function takes a size_t type as the size requested by malloc() and an integer that represents
 the maximum size for a particular Linked List, and it creates a BibopHeader meant to be placed at the top of
 this Linked List. Additionally, the passed-in BibopHeader is used for bookkeeping; it will be the next
 header pointed to by the resulting BibopHeader. The idea is to keep pushing BibopHeaders to the front
 of the list (like a stack) when we create new ones. The newly created BibopHeader will be placed at the head
 of the linked list of its boundary.

 This function returns the resulting new BibopHeader.

 **/

BibopHeader * createBibopHeader(malloc_t & mallocInfo) {

	//declare locals
	size_t sz = mallocInfo.mallocSize;
	int maxSize = mallocInfo.maxBoundary;

	//Allocate for the BibopHeader using mmap
	void * ptr;
	if (maxSize > 1024)
		ptr = mmap(NULL, (sz + sizeof(BibopHeader)), PROT_READ | PROT_WRITE,
				MAP_PRIVATE, fd, 0);
	else
		ptr
				= mmap(NULL, (PAGESIZE), PROT_READ | PROT_WRITE, MAP_PRIVATE,
						fd, 0);

	BibopHeader * newHeader = new (ptr) BibopHeader;
	//cout << "CREATED BibopHeader at Location: " << ptr << " With " << maxSize
	//		<< " byte-sized Chunks" << endl;

	newHeader->_objectSize = maxSize; //Slice the Page into maxSize chunks;
	newHeader->_next = NULL;
	newHeader->_prev = NULL;

	/*** Setup availability of page ***/
	//(No availability if block is dominated by malloc size requests > 1024)
	if (maxSize > 1024)
		newHeader->_available = 0;
	else
		// EG: (4096/15) = # of Chunks, minus 1 due to allocated object.
		newHeader->_available = (((PAGESIZE - sizeof(BibopHeader)) / maxSize)
				- 1);

	//cout << "There are " << newHeader->_available
	//		<< " chunks REMAINING in this header " << endl;
	/*** FreeObject Setup *****/

	/*  Consider a pointer to a starting address of free space. the value of that pointer
	 is the address of the next available slice in this page. In this manner, I can
	 maintain a linkedlist of free spaces.*/

	//This will be the FIRST available space to return to the
	//malloc request after creating this BibopHeader Page
	FreeObject * mallocAddress = ((FreeObject *) ptr + sizeof(BibopHeader));

	//cout << "This BibopHeader starts new space at " << (void *) mallocAddress
	//		<< endl;

	//For every available chunk...
	for (int i = 0; i < newHeader->_available; i++) {

		FreeObject ** object = (FreeObject **) ((i * newHeader->_objectSize)
				+ mallocAddress);
		/*
		 if(i == newHeader->_available - 1)
		 object = NULL;
		 else
		 *object = ((FreeObject *) object + newHeader->_objectSize); */

		*object = ((FreeObject *) object + newHeader->_objectSize);
	}

	//Point to the next available space in the Page
	if (newHeader->_available > 0)
		newHeader->_freeList = (FreeObject *) mallocAddress
				+ newHeader->_objectSize;

	//cout << "The FreeList for this NEW BibopHeader points to "
	//		<< (void *) newHeader->_freeList << endl;

	//g_headerList [10] = newHeader;

	//The malloc_t void pointer needs to point to the next
	//freely available space after the BibopHeader!
	// (This is what we're going to return from malloc())
	mallocInfo.mallocSpace = (void *) mallocAddress;

	return newHeader;
}

/** malloc() - allocates memory based on the size struct passed in:

 1) Allocates Memory
 2) Adjusts the Size of the Heap as required using sbrk().
 3) Look at MMAP_THRESHOLD **/
void * Allocator::malloc(size_t sz) {

	//If the size of the object is 0, then don't allocate anything
	if (sz == 0)
		return NULL;

	short index = lookupIndex(sz); //Look up the index for the header List

	//cout << "MALLOC Request of size " << sz << endl;

	//Our Go-To struct for maintaining important information
	malloc_t mallocInfo;
	mallocInfo.pageIndex = index;
	mallocInfo.mallocSize = sz;

	//The BigList needs an object-size...
	//ObjectSizes for the BigList (mallocs > 1024)
	if (index >= 8)
		mallocInfo.maxBoundary = sz; //Let the Max Boundary be the same size as the malloc request.
	else
		mallocInfo.maxBoundary = (int) pow(2, (index + 3)); //Threshold for memory placement (e.g. 2^3 = array [0])

	//cout << "This request has a MAX BOUNDARY of " << mallocInfo.maxBoundary
	//		<< endl;

	//For mallocs > 1024: We don't have to keep track of them!
	if (index >= 8)
		createBibopHeader(mallocInfo);

	//If the List is EMPTY (base case)
	else if (g_headerList[index] == NULL) {

		//Create the new BibopHeader that will be the head of this List
		BibopHeader * newHeader = createBibopHeader(mallocInfo);

		//Place it at the top of the List
		g_headerList[index] = newHeader;
	}

	//Regular Case!
	else
		allocateObject(mallocInfo);

	//cout << "Malloc Request RETURN of " << mallocInfo.mallocSpace << endl;
	//cout << "================================================" << endl;

	//Finally, return the malloc space
	return mallocInfo.mallocSpace;

}

/* allocateObject():

 This function accepts a malloc_t Info struct and attempts to allocate memory for this struct's
 mallocspace ptr to complete the malloc request. If the top header from the boundary list is
 full, then a new header will be created and placed at the head of the list, which will then lend
 the starting address to the mallocspace. On the other hand, if the top header from the list is
 not full, then the next available address from it will be sent to mallocspace and returned to the
 User.

 */
void allocateObject(malloc_t & mallocInfo) {

	short pageIndex = mallocInfo.pageIndex;

	//Remember, the header should NOT be NULL...
	BibopHeader * header = g_headerList[pageIndex];
	//cout << "Attempting to allocate an object from header " << header << endl;

	//If the current header is FULL...
	if (headerIsFull(header) == true) {

		//cout
		//		<< "1111111111111111111111111111111111111111111111111111111111111111111111111111111"
		//		<< endl;
	//	cout << "Header " << header
		//		<< " is FULL. Allocating a new BibopHeader.... " << endl;

		//Create a new one and make it the HEAD
		BibopHeader * newHeader = createBibopHeader(mallocInfo);

		//cout << "Making this new Header the Head of the Header List..." << endl;

		//Make this header the new Head of the List
		g_headerList[pageIndex] = newHeader;

		//cout << "Re-tying the List: HeaderList [" << newHeader << "]" << endl;

		/*** Re-tie the List! ***/
		newHeader->_next = header->_next;

		//cout << "More Retying: NewHeader->Next [" << newHeader->_next << "]"
		//		<< endl;

		//If the next Header isn't NULL, then tie it with
		//the new Header (and send the Full one to the Full List)
		if (header->_next != NULL)
			header->_next->_prev = newHeader;

		cout << "Sending header " << header << " to the Full List" << endl;

		//Move full header to the Full List!
		moveHeaderToFullList(header);

	//	cout
	//			<< "1111111111111111111111111111111111111111111111111111111111111111111111111111111"
	//			<< endl;

	} else { //There's room to allocate on the current Head

		//Grabbing the pointer to the next available chunk in this page header...
		FreeObject ** mallocPtr = (FreeObject **) header->_freeList;
		header->_available--;

		//Have MallocSpace point to the available space pointed by the FreeList
		mallocInfo.mallocSpace = (void *) mallocPtr;

		//Advance the FreeList to the next free space
		header->_freeList = (FreeObject *) (*mallocPtr);

		//cout << "ALLOCATING an object from header " << header << " at address "
		//		<< (void *) mallocPtr
		//		<< " and the freeList pointer pointing to the next space at "
		//		<< (void *) header->_freeList << endl;
	//	cout << "There are " << header->_available
		//		<< " chunks remaining from this header " << endl;

	}//end else

}//end allocateObject();


/* moveHeaderToFullList() and moveHeaderToBoundaryList():

 A function for moving a Bibopheader to the top of a
 BibopHeader pointer LinkedList. This is useful for moving
 full headers to a particular list (allocateObject()) or for
 moving headers to a list when it's not full anymore (i.e. when
 an object has been freed in the page (free())).

 */
void moveHeaderToFullList(BibopHeader * header) {

	//cout << "000000000000000000000000000000000000000000000000000000000000"
	//		<< endl;
	//cout << " Moving header " << header
	//		<< " To the Head of the Full List which is " << g_fullList << endl;

	if (g_fullList != NULL) {

		//Let the value of the header's next pointer
		//be the real BibopHeader at the head of the FullList
		header->_next = g_fullList;
		header->_prev = NULL;

		//Check if the head wasn't already a header
		//and Tie them together
		g_fullList->_prev = header;

		//cout << "Header [" << header << "] -> FullList [" << g_fullList
		//		<< "] -> " << g_fullList->_next << endl;

	} else {

		header->_next = NULL;
		header->_prev = NULL;
	//	cout << "Header [" << header << "] -> FullList [" << g_fullList
		//		<< "] ->  0" << endl;

	}

	//Have the header be the new head
	//of the Full List
	g_fullList = header;

	//if (g_fullList == NULL)
	//	cout << "Header [" << header << "] -> FullList [" << g_fullList
	//			<< "] -> 0 " << endl;
	//else
	////	cout << "Header [" << header << "] -> FullList [" << g_fullList
	//			<< "] -> " << g_fullList->_next << endl;

}

void moveHeaderToHeaderList(BibopHeader * header, short pageIndex) {

	//cout << "00000000000000000000000000000000000000000000000000000000000000"
	//		<< endl;
	//cout << " Moving header " << header
	//		<< " To the Head of the HEADER List which is "
	///		<< g_headerList[pageIndex] << " of maxSize " << header->_objectSize
	//		<< " and PageIndex " << pageIndex << endl;

	if (g_headerList[pageIndex] != NULL) {

		//cout << " Header->_next = the Head of the List " << endl;

		//Let the value of the header's next pointer
		//be the real BibopHeader at the head of the Header List
		header->_next = g_headerList[pageIndex];
		header->_prev = NULL;

		//cout << "Checking if the head wasn't already a header..." << endl;
		//cout << &(g_headerList[pageIndex]->_prev) << endl;

		//Check if the head wasn't already a header
		//and Tie them together
		(g_headerList[pageIndex])->_prev = header;

		//cout << "HEY!" << endl;

		//cout << "Header [" << header << "] -> HeaderList ["
		//		<< g_headerList[pageIndex] << "] -> "
		//		<< g_headerList[pageIndex]->_next << endl;

	} else {

		header->_next = NULL;
		header->_prev = NULL;
		//cout << "Header [" << header << "] -> HeaderList ["
		//		<< g_headerList[pageIndex] << "] ->  0" << endl;

	}

	//Have the header be the new head
	//of the Header List
	g_headerList[pageIndex] = header;

	//if (g_headerList[pageIndex] == NULL)
		//cout << "Header [" << header << "] -> HeaderList ["
			//	<< g_headerList[pageIndex] << "] -> 0 " << endl;
	//else
		//cout << "Header [" << header << "] -> HeaderList ["
			//	<< g_headerList[pageIndex] << "] -> "
			//	<< g_headerList[pageIndex]->_next << endl;

}

bool headerIsFull(BibopHeader * header) {

	//cout << "Is the Header FULL? FreeList-> " << (void *) header->_freeList
	//		<< " and #available = " << header->_available << endl;

	if (header->_freeList == NULL && header->_available == 0)
		return true;
	else
		return false;
}

/** lookupIndex():
 *
 * This function accepts a size_t data type that determines at which index
 * we will allocate memory for an object of that particular size. Returns 8
 * if the size is bigger than 1024, in which case it will be allocated on a separate
 * BibopHeader outside of the BibopHeader array.
 *
 */
short lookupIndex(size_t size) {

	//Allocate 8 bytes
	if (size > 0 && size <= 8)
		return 0;
	//Allocate 16 bytes
	else if (size > 8 && size <= 16)
		return 1;
	//Allocate 32 bytes
	else if (size > 16 && size <= 32)
		return 2;
	//Allocate 64 bytes
	else if (size > 32 && size <= 64)
		return 3;
	//Allocate 128 bytes
	else if (size > 64 && size <= 128)
		return 4;
	//Allocate 256 bytes
	else if (size > 128 && size <= 256)
		return 5;
	//Allocate 512 bytes
	else if (size > 256 && size <= 512)
		return 6;
	//Allocate 1024 bytes
	else if (size > 512 && size <= 1024)
		return 7;
	else
		return 8;
}

void Allocator::free(void * ptr) {

	BibopHeader * header = getBibopHeader(ptr);
	size_t size = header->_objectSize;
	short pageIndex = lookupIndex(size);

	//cout << "FREEING the header " << header << " at the address " << ptr
	//		<< endl << "The FreeList pointer is pointing at "
	//		<< (void *) header->_freeList << endl;
	//cout << "The SIZE of these chunks are " << (int) size << endl;

	//If this is an OVERSIZED Header...
	if (size > 1024) {

		//cout << " This is an OVERSIZED Header. REMOVING..." << endl;
		munmap((void *) header, (size + sizeof(BibopHeader)));

	}

	//This is a Regular Header...
	else {

		/*** Freeing the Chunk ****/

		/* Recipe: create a pointer to the current targeted slice (slice that is to be freed)
		 to read the value (pointer to the next available space). Set this value to the head
		 of the freeList so that the newly freed space is connected to the list. Finally,
		 set the Header's freeList pointer to the newly freed space.	   */

		//Get the right pointer with double-pointers!
		FreeObject ** object = (FreeObject **) ptr;

		//If the Header is FULL, remove it from the Full List
		//And Free an Object!
		if (headerIsFull(header) == true) {

			//If this page was in the Full List,
			 //Then get rid of the ties
			if(header == g_fullList)
				g_fullList = header->_next;

			if(header == g_headerList[pageIndex])
				g_headerList[pageIndex] = header->_next;

			if (header->_prev != NULL)
				header->_prev->_next = header->_next;

			if (header->_next != NULL)
				header->_next->_prev = header->_prev;

			//The value of this pointer will be the next
			//pointer on the freeList
			*object = header->_freeList;

			//Finally, set the freeList ptr to the new space
			header->_freeList = (FreeObject *) ptr;
			header->_available++;

			//cout << "The freeList pointer now points to " << ptr << endl;
			//cout << "The header has " << header->_available
					<< " chunks REMAINING. " << endl;
			//cout << "The header " << header
			//		<< " WAS FULL, but now we are moving this header to the Header List "
			//		<< endl;
			moveHeaderToHeaderList(header, pageIndex);

		}//end if HeaderIsFull()

		else { //The Header is NOT FULL.

			//The value of this pointer will be the next
			//pointer on the freeList
			*object = header->_freeList;

			//Finally, set the freeList ptr to the new space
			header->_freeList = (FreeObject *) ptr;
			header->_available++;

			//cout << "The freeList pointer now points to " << ptr << endl;
			//cout << "The header has " << header->_available
			//		<< " chunks REMAINING. " << endl;

		}//end else conditional (Header was not full when Freeing...)

		/*********** Last Check on Empty Pages *************/

		int available =
				((PAGESIZE - sizeof(BibopHeader)) / header->_objectSize);

		//If the header is fully available (i.e. the page is EMPTY)...
		if (header->_available == available) {

			//cout << "This header is COMPLETELY EMPTY. REMOVING..." << endl;
			//cout << "HeaderList [" << g_headerList[pageIndex] << "] and FullList [" << g_fullList << "] " << endl;

			if(header == g_fullList)
				g_fullList = header->_next;

			if(header == g_headerList [pageIndex])
				g_headerList [pageIndex] = header->_next;

			if (header->_prev != NULL)
				header->_prev->_next = header->_next;

			if (header->_next != NULL)
				header->_next->_prev = header->_prev;


			//Release the Page to the Operating System
			munmap((void *)header, PAGESIZE);

			//cout << "HeaderList [" << g_headerList[pageIndex] << "] and FullList [" << g_fullList << "] " << endl;

		}//end if

	}//end else conditional

	//cout << "++++++++++++++++++++++++++++++++++++++++++++++++++" << endl;

} //end free();


/* getSize():
 *
 * Return the Size of the allocation pointed by the pointer.
 *
 */
size_t Allocator::getSize(void * ptr) {

	BibopHeader * header = getBibopHeader(ptr);
	return header->_objectSize;
}

/* getBibopHeader ():
 *
 * This function accepts a void pointer to an unknown address
 * and returns the BibopHeader Page that this address is assoiated
 * with.
 */
BibopHeader * getBibopHeader(void * ptr) {

	//Convert to a malleable type
	FreeObject * addr = (FreeObject *) ptr;
	unsigned long address = (unsigned long) addr;

	//Now, address mod PAGESIZE gives us the distance between the pointer
	//and the pointer to the BibopHeader (since the address of any BibopHeader
	//is a multiple of 4096. Subtract the difference from the address of the pointer
	//to find the BibopHeader pointer!
	void * bibopPtr = (void *) (address - (address % PAGESIZE));
	BibopHeader * result = (BibopHeader *) bibopPtr;
	return result;

}
