#pragma once

#include "Mem.h"
#include "Process.h"
#include "ScanResult.h"

class MemScan
{
public:
        MemScan(void);
        MemScan(Process *process);
        ~MemScan(void);

public:
        void    Init();

        void            Create(int data_size);
        void            Update2(SEARCH_CONDITION cond, char *value);
        void            Update(SEARCH_CONDITION cond, unsigned int value);

        
        void            Dump_Scan_Info();
        void            Dump_Scan_Match();

        void            GetMatches(ScanResult *scanresult);

        bool            MemWrite();

        void            Free();

protected:
        MEMBLOCK        *Create_Memblock(HANDLE hProc, MEMORY_BASIC_INFORMATION *meminfo, int data_size);
        
        void            Update_Memblock(MEMBLOCK *mb, SEARCH_CONDITION cond, unsigned int value);
        void            Free_Mem(MEMBLOCK *mb);
        
        bool            Poke(HANDLE proc, unsigned int addr, unsigned int value);

        UINT            Peek(HANDLE proc, int data_size, unsigned int addr);

private:
        int                     result_count;
        
        int                     _size;
        MEMBLOCK        *scan_result;
        Process         *proc;
};