#pragma once

#include "common.h"

typedef struct _RESULT
{
        int id;
        
        char *addr;
        
        U_INT value;
        
        _RESULT *next;

} RESULT;

class ScanResult
{
public:
        ScanResult(void);
        ~ScanResult(void);

public:
        void Add(int id, char *addr, unsigned int value);
        void Free();
        void Debug();

private:
        std::list<RESULT *> results;
        
};