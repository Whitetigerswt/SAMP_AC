#ifndef __MEM_INC__
#define __MEM_INC__

#include "common.h"

#define IS_IN_SEARCH(mb, offset) (mb->searchmask[(offset / 8)] & (1<<((offset)%8)))
#define REMOVE_FROM_SEARCH(mb, offset) mb->searchmask[(offset / 8)] &= ~1<<((offset)%8);
#define WRITABLE (PAGE_READWRITE | PAGE_WRITECOPY | PAGE_EXECUTE_READWRITE | PAGE_EXECUTE_WRITECOPY)

typedef struct _MEMBLOCK
{
        int id;
        int matches;
        int data_size;

        UCHAR *addr;
        UCHAR *buffer;
        UCHAR *searchmask;

        HANDLE hProc;
        SIZE_T size;

        struct _MEMBLOCK*next;

} MEMBLOCK;

typedef enum
{
        C_NONE,
        C_EQUAL,
        C_INC,
        C_DEC,
        C_BYTES

} SEARCH_CONDITION;


typedef enum
{
        C_AOB = 0,
        C_SINGLE = 1,
        C_DOUBLE = 2,
        C_FLOAT = 4,

} SEARCH_TYPE;


#endif