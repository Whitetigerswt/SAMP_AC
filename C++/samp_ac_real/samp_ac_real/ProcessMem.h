#ifndef _PROCESS_MEM_INCLUDED_
#define _PROCESS_MEM_INCLUDED_

#include "main.h"
#include <windows.h>
#include <tlhelp32.h>
#include <iostream>
#include <psapi.h>
#include <string>
using std::string;

class ProcessMem {
	public: 
		ProcessMem(char* window);
		~ProcessMem();
		void WriteProcessMem(int, char, int);
		void WriteProcessMem(int, int, int);
		void WriteProcessMem(int, double, int);

		char* ReadProcessMemStr(int, int);
		int ReadProcessMemInt(int, int);
		byte ReadProcessMemByte(int, int);
		double ReadProcessMemDouble(int, int);
		string ReadProcessMemDWORD(int, int);
		bool ProcessMem::CheckProcess();
		HANDLE GetProcess();

		int GetModuleBaseAddress(char* module);
	private:
		const char * windowName;
		DWORD processId;
		HANDLE process;
		void SetTokenStuff( HANDLE process_ );
		char buffer[64];
		bool strcmp2( char*, char* );
};



#endif // _PROCESS_MEM_INCLUDED_