#include "Process.h"

Process::Process(void)
{
        Init();
}

Process::~Process(void)
{
        if(hInst)
                hInst = NULL;
        if(hProc)
                hProc = NULL;
        if(hWnd)
                hWnd = NULL;
        if(proc_id)
                proc_id = NULL;
}

void Process::Init()
{
        hInst = NULL;
        hProc = NULL;
        hWnd = NULL;
        proc_id = NULL;
}

bool Process::OpenProc(const char *szProcess)
{
        hWnd = FindWindow(NULL, szProcess);

        if(hWnd != NULL)
        {
                GetWindowThreadProcessId(hWnd, &proc_id);
                hProc = OpenProcess(PROCESS_ALL_ACCESS, FALSE, proc_id);
                if(hProc) return true;
        }
        return false;
}

void Process::CloseProc()
{
        if(hProc)
                CloseHandle(hProc);
        hProc = NULL;
}