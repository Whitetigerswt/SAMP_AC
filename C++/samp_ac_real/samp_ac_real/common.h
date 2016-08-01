#ifndef __COMMON_INC__
#define __COMMON_INC__

#include <windows.h>
#include <stdio.h>
#include <iostream>

#include <list>

typedef unsigned int  U_INT;
typedef unsigned char U_CHAR;

#define DEBUG

#ifdef DEBUG
        #define TRACE(msg) printf("*****>> %s\r\n", msg)
#else
        #define TRACE(msg)
#endif

#pragma warning(disable: 4996)

#endif