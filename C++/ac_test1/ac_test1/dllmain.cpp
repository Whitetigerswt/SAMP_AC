// dllmain.cpp : Defines the entry point for the DLL application.
//#define _CRT_SECURE_NO_WARNINGS
#include "stdafx.h"
#include <stdio.h>
//#include <iostream>
#include <fstream>
#include <windows.h>

#include <winternl.h>
//#include <ntstatus.h>
#include <tlhelp32.h>
#include "md5.h"
#include "multiplayer_hooksystem.h"

#include <windows.h>
#include <tlhelp32.h>
//#include <iostream>
#include <psapi.h>
//#include <string>

typedef NTSTATUS (WINAPI *pNtQIT)(HANDLE ThreadHandle, THREADINFOCLASS ThreadInformationClass, PVOID ThreadInformation, ULONG ThreadInformationLength, PULONG ReturnLength );


typedef void (WINAPI *PGNSI)(HANDLE hProcess, DWORD dwFlags, LPSTR lpExeName, PDWORD lpdwSize );


int modules = 0;
bool firstload = false;
//int runType = 1;

void SetTokenStuff();
DWORD ourthread[2];

//#define SAMP_AC_HASH "58389fb0ebf4b648b1c28ebfb764d7f5"

bool strcmp2(char string1[], char string2[])
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

static void WINAPI Load();
static void WINAPI UnLoad();
void EnableDebugPriv();
void CheckProcesses();
int CheckModules(DWORD startaddress);
int GetszFile (char *szFileName);

DWORD GetModuleBaseAddress(char* module) {
	return (DWORD)GetModuleHandle (module);
}


HANDLE suspendedthreads[500];

HMODULE ourhMod = NULL;

PGNSI pGNSI = (PGNSI) GetProcAddress( GetModuleHandle("Kernel32.dll"), (LPCSTR)"QueryFullProcessImageNameA");

DWORD WINAPI GetThreadStartAddress(DWORD tid)
{

	NTSTATUS ntStatus;
	HANDLE hDupHandle;
	DWORD dwStartAddress;

	HANDLE hProcess;

	HANDLE hTread;

	hTread = OpenThread(THREAD_QUERY_INFORMATION, FALSE, tid);

	pNtQIT NtQueryInformationThread;
	NtQueryInformationThread = (pNtQIT)GetProcAddress(GetModuleHandle("ntdll.dll"), "NtQueryInformationThread");

	if(NtQueryInformationThread == NULL)
		return 0;

	hProcess = GetCurrentProcess();
	if(!DuplicateHandle(hProcess, hTread, hProcess, &hDupHandle, THREAD_QUERY_INFORMATION, FALSE, 0)){

		SetLastError(ERROR_ACCESS_DENIED);

		return 0;

	}

	ntStatus = NtQueryInformationThread(hDupHandle, (THREADINFOCLASS)9, &dwStartAddress, sizeof(DWORD), NULL);
	CloseHandle(hTread);
	CloseHandle(hProcess);

	CloseHandle(hDupHandle);

	if (ntStatus != 0)
		return 0;

	return dwStartAddress;
}

bool fexists(const char *filename) {
  std::ifstream ifile(filename);
  if(ifile) return true; 
  else return false;
}

char OurDir();

BOOL APIENTRY DllMain( HMODULE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
					 )
{
	switch (ul_reason_for_call)
	{
		case DLL_PROCESS_ATTACH: {

			ourhMod = hModule;

			if(CheckModules(GetThreadStartAddress(GetCurrentThreadId())) == 0) {
				ExitThread(0);
				return true;
			}



			if(!firstload) {
				/*ifstream ifile("ac.cfg");
				if(!ifile) {
					int msgbox = MessageBox(*(HWND*)0xC97C1C, "Load ac.asi only if SAMP_AC is already running? \n\nNote: If you chose Yes, you will have to restart your game and have Anti-cheat already running before you launch your game again to play with Anti-cheat. \nIf you chose No then you won't have to restart your game but you may experience more game crashes.", "ac.asi loader", MB_YESNO | MB_SETFOREGROUND | MB_ICONINFORMATION);
					if(msgbox == IDNO) {

						ofstream ofile("ac.cfg");
						ofile << "run 1" << endl;

						ofile.close();

						return FALSE;

					} else if(msgbox == IDYES) {

						ofstream ofile("ac.cfg");
						ofile << "run 0" << endl;

						ofile.close();
						return FALSE;
					}
				} 

				string type = "";
				int enabled;
				ifstream iifile("ac.cfg");
				if(iifile >> type >> enabled) {
					if(type.compare("run") == 0) {
						runType = enabled;
					}
				}*/

				EnableDebugPriv();
				ourthread[0] = GetCurrentThreadId();
				firstload = true;

				CheckProcesses();
				if (CreateThread( 0, 0, (LPTHREAD_START_ROUTINE)Load, NULL, 0, 0) == NULL ) {
					ExitProcess(GetLastError());
					return FALSE; 
				} else return TRUE;

			}
			return TRUE;
		}
		case DLL_THREAD_ATTACH: {
			if(CheckModules(GetThreadStartAddress(GetCurrentThreadId())) == 0) {
				ExitThread(0);
				return true;
			}
			CheckProcesses();

			for(int i=0; i < sizeof(ourthread); ++i) {
				if(ourthread[i] == GetCurrentThreadId()) {
					ExitProcess(0);
				}
			}
			return TRUE;
		}
		case DLL_PROCESS_DETACH: { 

		}
		case DLL_THREAD_DETACH: {

		}
	}
	return TRUE;
}

void EnableDebugPriv()
{
    HANDLE hToken;
    LUID luid;
    TOKEN_PRIVILEGES tkp;

    OpenProcessToken(GetCurrentProcess(), TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY, &hToken);

    LookupPrivilegeValue(NULL, SE_DEBUG_NAME, &luid);

    tkp.PrivilegeCount = 1;
    tkp.Privileges[0].Luid = luid;
    tkp.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;

    AdjustTokenPrivileges(hToken, false, &tkp, sizeof(tkp), NULL, NULL);

    CloseHandle(hToken); 
}

static void WINAPI UnLoad() {
	Sleep(1000);
	FreeLibraryAndExitThread(ourhMod, GetLastError());
}


static void WINAPI Load() {
	//endthreads = true;
	ourthread[1] = GetCurrentThreadId();

	char exedir[256];

	char processpath[256];
	
	if(pGNSI != NULL) {
		DWORD t = (DWORD)sizeof(processpath);
		pGNSI(GetCurrentProcess(), 0, processpath, &t);
	}
	else {
		GetModuleFileNameEx(GetCurrentProcess(), NULL, processpath, sizeof(processpath));
	}

	sprintf_s(exedir, sizeof(exedir), "%s", processpath);
	exedir[strlen(exedir) - 10] = '\0';
	strcat(exedir, "d3d9.dll");

	if(!fexists(exedir)) {
		SetLastError(2);
		FreeLibraryAndExitThread(ourhMod, 2);
	} else {
		MD5 md5_ = MD5();
		char * md5 = md5_.digestFile(exedir);
		if(strcmp2("2f05c2c74c5c7061a8cbaa03f92d1c4f", md5) && strlen(md5) > 0) {

		} else{

			FreeLibraryAndExitThread(ourhMod, 0);
		}
	}

	while(*(PDWORD)0xC8D4C0 <= 6) {
		Sleep(5);
	}
}

void CheckProcesses() {
	

	HANDLE hProcessSnap = INVALID_HANDLE_VALUE; 
	PROCESSENTRY32 pe32; 
	hProcessSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);

	if(pGNSI == NULL) {
		pGNSI = (PGNSI) GetProcAddress( GetModuleHandle("Kernel32.dll"), (LPCSTR)"QueryFullProcessImageNameA");
	}

	if (hProcessSnap != INVALID_HANDLE_VALUE) 
	{
		pe32.dwSize = sizeof(PROCESSENTRY32); 
		if (Process32First(hProcessSnap, &pe32)) 
		{
			while(Process32Next(hProcessSnap, &pe32))
			{
				char processpath[256];
				HANDLE pHandle = NULL;
				pHandle = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, TRUE, pe32.th32ProcessID);
				if(pHandle != NULL) {
					DWORD  t = (DWORD)sizeof(processpath);

					if(pGNSI != NULL) {
						pGNSI(pHandle, 0, processpath, &t);
					}
					else {
						GetModuleFileNameEx(pHandle, NULL, processpath, sizeof(processpath));
					}

					if(processpath != NULL) {
						int size = GetszFile(processpath);

						if(size == 271360 || size == 187392 || size == 159744 || size == 68096 || size == 843776 || size == 532480 || size == 2434560 || size == 3047424) {
							MD5 md5_ = MD5();
							char * md5 = md5_.digestFile(processpath);

							if(strcmp2(md5, "ecc00d4e4b2cbf7caefdce122f017c3d") ||
								strcmp2(md5, "7f244cdbb31d946deadc207d00c3fb19") ||
								strcmp2(md5, "5e57320eb89377a7ab05cd8a8a530c27") ||
								strcmp2(md5, "1dbb21e7ef1732f3235227e2a9d84c23") ||
								strcmp2(md5, "805917de70747b1eda47095b5fed15f1") ||
								strcmp2(md5, "5c71bf80b291452cff22524688d38297") ||
								strcmp2(md5, "f5590d99951b736c731585882bb2bbe3") ||
								strcmp2(md5, "09fd80151097df2a80f014c6fd2d1480") ||
								strcmp2(md5, "fe4c06443a4bee9e65db62958d70927a") ||
								strcmp2(md5, "6c38fce7a4f4c637864c416b663598fd") ||
								strcmp2(md5, "7af8898570ca1a73d9890b0e0349b2a8")
								) {
								ExitThread(0);
							
								return;
							}
						}
						/*if(strcmp2(md5, SAMP_AC_HASH)) {
							runType = 1;
						}*/
					}
				} else {

				}
			}
		}
	}
}

int GetszFile (char *szFileName)
{
 DWORD dwSize;
 HANDLE hFile;
    hFile = CreateFile (szFileName,
                        GENERIC_READ,
                        FILE_SHARE_READ,
                        NULL,
                        OPEN_EXISTING,
                        FILE_ATTRIBUTE_READONLY,
                        NULL);
dwSize = GetFileSize (hFile,
                       NULL); 
 CloseHandle(hFile);
 return dwSize;
}

//ofstream logfile("ac.log");

int CheckModules(DWORD startaddress) {
	EnableDebugPriv();
	int AllowedThreads[256] = { 
		// HOODLUM EXE //
		0x824570,
		0x8245B0,
		0x406560,
		0x4EEE90,
		0x4F15C0,
		// American EXE
		0x12FFE40,
		0x117C160,
		0x11804D0,
		0x1184980,
		0x1188BA0,
		0x11989D0,
		0x11A04C0,
		0x119C700,
		0x118C8b0,
		0x1190B20,
		0x11A7E10,
		0x1194980,
		0x11A3D10,
		0x11AFE40,
		0x11ABF80,
		0x11B3CE0,
		0x11B7570,
		0x11B3BE0,
		// unknown
		0x16D8CD7, // hambaken
		0x18D8CD7, // hambaken
		0x15D8CD7, // hambaken
		0x4F4A20, // orange and homer



		GetModuleBaseAddress("samp.dll") + 0xB1063, // samp main thread, launched when we start the game and are "connecting" to the server. 0.3x r1-2
		GetModuleBaseAddress("samp.dll") + 0xB0C93, // samp 0.3x NOT r1-2
		GetModuleBaseAddress("samp.dll") + 0xB10F3, // 3x R2 connect thread
		GetModuleBaseAddress("msvcrt.dll") + 0x28D6F,
		GetModuleBaseAddress("msvcrt.dll") + 0x19102,
		GetModuleBaseAddress("DSOUND.dll") + 0x268D,
		GetModuleBaseAddress("DSOUND.dll") + 0x1F0B7,
		GetModuleBaseAddress("DINPUT8.dll") + 0x937C,
		GetModuleBaseAddress("nvd3dum.dll") + 0x83C414,
		GetModuleBaseAddress("nvd3dum.dll") + 0xDEB0,
		GetModuleBaseAddress("crashes.asi") + 0x26C0,
		GetModuleBaseAddress("ac.asi") + 0x2020,
		GetModuleBaseAddress("samp.dll") + 0xAFAD2, // samp 0.3x not R1-2
		GetModuleBaseAddress("samp.dll") + 0xAFEA2, // sa-mp main thread started when load screen shows up 0.3x r1-2
		GetModuleBaseAddress("kernal32.dll") + 0x1F82B,
		GetModuleBaseAddress("sensfix.asi") + 0x9506,
		GetModuleBaseAddress("kernal32.dll") + 0x1B50A,
		GetModuleBaseAddress("DSOUND.dll") + 0x4A91,
		GetModuleBaseAddress("ntdll.dll") + 0x15658,
		GetModuleBaseAddress("d3d9.dll") + 0x132075,
		GetModuleBaseAddress("DINPUT8.dll") + 0xC2D5,
		GetModuleBaseAddress("msvcrt.dll") + 0x10CA7,
		GetModuleBaseAddress("msvcrt.dll") + 0x3EDC7,
		
	};
	if(startaddress == 0) {
		return 0;
	}
	for(int i=0; i < sizeof(AllowedThreads); ++i) {
		if(AllowedThreads[i] == 0) break;
		if(AllowedThreads[i] == startaddress) return 1;
	}

	//ofstream logfile("ac.log");

	MODULEENTRY32 me32;
	bool range = false;
	HANDLE hModuleSnap = INVALID_HANDLE_VALUE;
	hModuleSnap = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE, GetCurrentProcessId());
	if(hModuleSnap != INVALID_HANDLE_VALUE) {
		me32.dwSize = sizeof(MODULEENTRY32);
		if(Module32First(hModuleSnap, &me32)) {
			while(Module32Next(hModuleSnap, &me32)) {
				if(startaddress >= (DWORD)me32.modBaseAddr && startaddress < (DWORD)(me32.modBaseAddr + me32.modBaseSize)) {
					/*char str[256];
					sprintf(str, "0x%x - 0x%x %s", startaddress, (DWORD)me32.modBaseAddr, me32.szModule);
					logfile << str << endl;*/
					return 1;
				}
			}
		}
	}
	CloseHandle(hModuleSnap);
	/*char str[256];
	sprintf(str, "0x%x", startaddress);
	logfile << "not on list -> " << str << endl;*/
	return 0;
}