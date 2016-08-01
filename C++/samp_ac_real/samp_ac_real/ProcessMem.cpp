#include "ProcessMem.h"

ProcessMem::ProcessMem(char* window) : windowName(window) {
	HWND hWnd = FindWindow(NULL, this->windowName);
	if(hWnd != NULL) {
		GetWindowThreadProcessId(hWnd, &processId);

		process = OpenProcess(PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_VM_OPERATION | PROCESS_QUERY_INFORMATION, FALSE, this->processId);
	}
}

ProcessMem::~ProcessMem() {
	CloseHandle(this->process);
	delete this;
}

bool ProcessMem::CheckProcess() {

	HWND hWnd = FindWindow(NULL, this->windowName);
	if(hWnd != NULL) {
		GetWindowThreadProcessId(hWnd, &processId);

		if(process) {
			CloseHandle(process);
		}

		process = OpenProcess(PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_VM_OPERATION | PROCESS_QUERY_INFORMATION, FALSE, this->processId);

		return true;
	} else {
		return false;
	}
}

int ProcessMem::ReadProcessMemInt(int address, int size) {
	long int lpBuffer = address;

	int msg;
	if(process != NULL) {
		DWORD d = NULL;

		SetTokenStuff( this->process );
		VirtualProtectEx(process, (void*)lpBuffer, size, PAGE_EXECUTE_READWRITE, &d);
		ReadProcessMemory(process, (void*)lpBuffer, &msg, size, NULL);
		VirtualProtectEx(process, (void*)lpBuffer, size, d, NULL);
			
	}

	return msg;
}


byte ProcessMem::ReadProcessMemByte(int address, int size) {
	long int lpBuffer = address;

	byte msg;
	if(process != NULL) {
		DWORD d = NULL;

		SetTokenStuff( this->process );
		VirtualProtectEx(process, (void*)lpBuffer, size, PAGE_EXECUTE_READWRITE, &d);
		ReadProcessMemory(process, (void*)lpBuffer, &msg, size, NULL);
		VirtualProtectEx(process, (void*)lpBuffer, size, d, NULL);
			
	}

	return msg;
}

char* ProcessMem::ReadProcessMemStr(int address, int size) {
	long int lpBuffer = address;


	char msg[32];
	if(process != NULL) {
		DWORD d = NULL;

		SetTokenStuff( this->process );
		VirtualProtectEx(process, (void*)lpBuffer, size, PAGE_EXECUTE_READWRITE, &d);
		ReadProcessMemory(process, (void*)lpBuffer, &msg, size, NULL);
		VirtualProtectEx(process, (void*)lpBuffer, size, d, NULL);
			
	}
	int i = strlen(msg);
	if(i > size) {
		msg[size] = '\0';
	}
	sprintf_s(buffer, "%s", msg);

	return buffer;
}

string ProcessMem::ReadProcessMemDWORD(int address, int size) {
	long int lpBuffer = address;

	DWORD msg[32];
	if(process != NULL) {
		DWORD d = NULL;

		SetTokenStuff( this->process );
		VirtualProtectEx(process, (void*)lpBuffer, size, PAGE_EXECUTE_READWRITE, &d);
		ReadProcessMemory(process, (void*)lpBuffer, &msg, size, NULL);
		VirtualProtectEx(process, (void*)lpBuffer, size, d, NULL);
			
	}
	sprintf_s(buffer, "%d", msg);

	return buffer;
}

double ProcessMem::ReadProcessMemDouble(int address, int size) {
	long int lpBuffer = address;

	float msg;
	if(process != NULL) {
		DWORD d = NULL;

		SetTokenStuff( this->process );
		VirtualProtectEx(process, (void*)lpBuffer, size, PAGE_EXECUTE_READWRITE, &d);
		ReadProcessMemory(process, (void*)lpBuffer, &msg, size, NULL);
		VirtualProtectEx(process, (void*)lpBuffer, size, d, NULL);
			
	}
	return msg;
}

void ProcessMem::WriteProcessMem(int address, char towrite, int size) {
	long int lpBuffer = address;

	if(this->process != NULL) {
		DWORD d = NULL;
		SetTokenStuff( this->process );
		VirtualProtectEx(this->process, (void*)address, size, PAGE_EXECUTE_READWRITE, &d);
		WriteProcessMemory(this->process, (void*)address, &towrite, size, NULL);     // write the value 
		VirtualProtectEx(this->process, (void*)lpBuffer, size, d, NULL);
	}
}

void ProcessMem::WriteProcessMem(int address, int towrite, int size) {
	long int lpBuffer = address;

	if(this->process != NULL) {
		DWORD d = NULL;
		SetTokenStuff( this->process );
		VirtualProtectEx(this->process, (void*)address, size, PAGE_EXECUTE_READWRITE, &d);
		WriteProcessMemory(this->process, (void*)address, &towrite, size, NULL);     // write the value 
		VirtualProtectEx(this->process, (void*)lpBuffer, size, d, NULL);
	}
}

void ProcessMem::WriteProcessMem(int address, double towrite, int size) {
	long int lpBuffer = address;

	if(this->process != NULL) {
		DWORD d = NULL;
		SetTokenStuff( this->process );
		VirtualProtectEx(this->process, (void*)address, size, PAGE_EXECUTE_READWRITE, &d);
		WriteProcessMemory(this->process, (void*)address, &towrite, size, NULL);     // write the value 
		VirtualProtectEx(this->process, (void*)lpBuffer, size, d, NULL);
	}
}

void ProcessMem::SetTokenStuff( HANDLE process_ ) {
	HANDLE hToken;
	TOKEN_PRIVILEGES token_privileges;                  
    DWORD dwSize;                        
    ZeroMemory (&token_privileges, sizeof (token_privileges));

	OpenProcessToken( process_, TOKEN_ALL_ACCESS, &hToken );
	LookupPrivilegeValue( NULL, SE_DEBUG_NAME, &token_privileges.Privileges[0].Luid );
	AdjustTokenPrivileges ( hToken, FALSE, &token_privileges, 0, NULL, &dwSize );

	CloseHandle(hToken);
}

int ProcessMem::GetModuleBaseAddress(char* module) {
	HANDLE hModuleSnap = INVALID_HANDLE_VALUE; 
	MODULEENTRY32 me32; 
	hModuleSnap = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE, processId);
	if (hModuleSnap != INVALID_HANDLE_VALUE) 
	{
		me32.dwSize = sizeof(MODULEENTRY32); 
		if (Module32First(hModuleSnap, &me32)) 
		{
			while(Module32Next(hModuleSnap, &me32))
			{
				if( (strcmp2(me32.szModule, module)))
				{
					CloseHandle(hModuleSnap);
					return (int) me32.modBaseAddr;
				}
			}
		}
	}
	CloseHandle(hModuleSnap);
	return -1;
}

HANDLE ProcessMem::GetProcess() {
	return this->process;
}

bool ProcessMem::strcmp2(char string1[], char string2[])
{
	if (strlen(string1) == strlen(string2))
	{
		for(unsigned int i = 0; i < strlen(string1); i++)
		{
			if (tolower(string1[i]) != tolower(string2[i]))
			{
				return false;
			}
		}
	}else{
		return false;
	}
	return true;
}
