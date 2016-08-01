#ifndef __PROCESS_INC__
#define __PROCESS_INC__

#include "common.h"

class Process
{
public:
        Process(void);
        ~Process(void);

public:
        bool            OpenProc(const char *szProcess);
        void            CloseProc();

        HANDLE          *GetProc() { return &hProc; };
        DWORD           GetProcID() { return proc_id; };

protected:
        void            Init();

private:
        HINSTANCE       hInst;
        HANDLE          hProc;
        HWND            hWnd;
        
        DWORD           proc_id;
};

#endif