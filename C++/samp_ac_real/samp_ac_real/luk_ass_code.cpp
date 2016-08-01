/*#include "stdafx.h"
#include <windows.h>
#include <tlhelp32.h>
#include <iostream>
#include <ctime>
#include <stdio.h>
#include <string.h>
#include <windows.h>
#include <iostream>
#include <tlhelp32.h>
#include <conio.h>
#include <stdlib.h>
#include "Header2.h"

using namespace std;

#define FILE_LENGTH			262144

char chararray[FILE_LENGTH];
int pozice = 0;

bool tochar(char string[])
{
	int len = strlen(string);
	if (len < FILE_LENGTH)
	{
		for(int i = 0; i < len; i++)
		{
			chararray[pozice++] = string[i];
		}
	}else{
		for(int i = 0; i < FILE_LENGTH; i++)
		{
			chararray[pozice++] = string[i];
		}
	}
	return true;
}

bool list_files(char path[])
{
    WIN32_FIND_DATA FindFileData;
	HANDLE hFind = INVALID_HANDLE_VALUE;

	char DirSpec[MAX_PATH];
	sprintf_s(DirSpec, "%s\\*", path);
	hFind = FindFirstFile(DirSpec, &FindFileData);
	if(hFind == INVALID_HANDLE_VALUE)
    {
		return true;
    }
	while(FindNextFile(hFind, &FindFileData) != 0)
	{
		if (strcmp(FindFileData.cFileName, ".") != 0 && strcmp(FindFileData.cFileName, "..") != 0)
		{
			sprintf_s(DirSpec, "%s\\%s", path, FindFileData.cFileName);

			char buffer[MAX_PATH +20];
			FILE* file = fopen(DirSpec, "r");
			if (file)
			{
				fseek(file, 1, SEEK_END);
				sprintf_s(buffer, "  %s\\%s (%d B)\r\n", path, FindFileData.cFileName, ftell(file));
				fclose(file);
			}else{
				sprintf_s(buffer, "  %s\\%s (DIR)\r\n", path, FindFileData.cFileName);
				list_files(DirSpec);
			}
			tochar(buffer);
		}
	}
	FindClose(hFind);
	return true;
}

bool strcmp2(char string1[], char string2[])
{
	int len1 = strlen(string1);
	if (len1 == strlen(string2))
	{
		for(int i = 0; i < len1; i++)
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

JNIEXPORT jstring JNICALL Java_cplusplus_ReadMemoryAddr(JNIEnv *env, jclass obj, jstring MemAddress)
{
	tochar("Date of test: ");
	char buffer[11];
	_strdate_s(buffer);
	tochar(buffer);
	tochar(".\r\nTime of test: ");
	_strtime_s(buffer);
	tochar(buffer);
	tochar(".\r\n\r\n");


	HWND hWnd;
	DWORD pId;
	int cheat_type = 0;
	if (hWnd = FindWindow(NULL, "GTA:SA:MP"))
	{
		tochar("List of injected modules:\r\n");
		GetWindowThreadProcessId(hWnd, &pId);
		HANDLE pHandle = OpenProcess(PROCESS_ALL_ACCESS, TRUE, pId);

		DWORD samp_address = -1;
		char GTA_SA_path[MAX_PATH];
		char SAMP_path[MAX_PATH];

		HANDLE hModuleSnap = INVALID_HANDLE_VALUE; 
		MODULEENTRY32 me32; 
		hModuleSnap = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE, pId);
		if (hModuleSnap != INVALID_HANDLE_VALUE) 
		{
			me32.dwSize = sizeof(MODULEENTRY32); 
			if (Module32First(hModuleSnap, &me32)) 
			{
				while(Module32Next(hModuleSnap, &me32))
				{
					if (samp_address == -1)
					{
						if (strcmp2(me32.szModule, "samp.dll"))
						{
							samp_address = (DWORD) me32.modBaseAddr;
							int len = strlen(me32.szExePath);
							for(int i = 0; i < len; i++)
							{
								SAMP_path[i] = me32.szExePath[i];
								if (i < len -9) // -"/samp.dll"
								{
									GTA_SA_path[i] = me32.szExePath[i];
								}
							}
							SAMP_path[len] = '\0';
							GTA_SA_path[len -9] = '\0';
						}
					}
					printf("module name: %s\r\n", me32.szModule);
					tochar("  module name: ");
					tochar(me32.szModule);

					printf("executable: %s\r\n", me32.szExePath);
					tochar("\r\n  executable: ");
					tochar(me32.szExePath);

					printf("process ID: %d\r\n", me32.th32ProcessID); 
					tochar("\r\n  process ID: ");
					sprintf_s(buffer, "%d", me32.th32ProcessID);
					tochar(buffer);

					printf("ref count (g): %d\r\n", me32.GlblcntUsage); 
					tochar("\r\n  ref count (g): ");
					sprintf_s(buffer, "%d", me32.GlblcntUsage);
					tochar(buffer);

					printf("ref count (p): %d\r\n", me32.ProccntUsage); 
					tochar("\r\n  ref count (p): ");
					sprintf_s(buffer, "%d", me32.ProccntUsage);
					tochar(buffer);

					printf("base address: %d\r\n", (DWORD) me32.modBaseAddr); 
					tochar("\r\n  base address: ");
					sprintf_s(buffer, "%d", (DWORD) me32.modBaseAddr);
					tochar(buffer);

					printf("base size: %d\r\n", me32.modBaseSize); 
					tochar("\r\n  base size: ");
					sprintf_s(buffer, "%d", me32.modBaseSize);
					tochar(buffer);

					tochar("\r\n\r\n");
				}
				tochar("GTA:SA directory file list:\r\n");
				list_files(GTA_SA_path);

				FILE* file = fopen(SAMP_path, "r");
				int offset = -1;
				if (file)
				{
					fseek(file, 1, SEEK_END);
					offset = ftell(file);
					fclose(file);

					if (offset == 1486849 || offset == 1507329) // 0.3c R1 nebo R2
					{
						offset = 0x1F6E6D;
					}
					else if (offset == 1511425) // 0.3c R3
					{
						offset = 0x1F6D55;
					}
					else if (offset == 1765377) // 0.3d R1
					{
						offset = 0x20697D;
					}
					else if (offset == 2015233) // 0.3d R2
					{
						offset = 0x206985;
					}else{
						offset = -2;
					}
				}
				if (offset > 0)
				{
					tochar("\r\nThe game is connected to server ");

					int i = 0;
					while(chararray[pozice -1] != 0) // IP
					{
						ReadProcessMemory(pHandle, (LPCVOID)(samp_address +offset +i), &chararray[pozice++], 4, NULL);
						i++;
					}
					pozice--;
					chararray[pozice++] = ':';
					i = 0;
					while(chararray[pozice -1] != 0) // port
					{
						ReadProcessMemory(pHandle, (LPCVOID)(samp_address +offset +257 +i), &chararray[pozice++], 4, NULL);
						i++;
					}
					pozice--;
					tochar(" with nick ");
					i = 0;
					while(chararray[pozice -1] != 0) // nick
					{
						ReadProcessMemory(pHandle, (LPCVOID)(samp_address +offset +2*257 +i), &chararray[pozice++], 4, NULL);
						i++;
					}
					pozice--;
					chararray[pozice++] = '.';
				}
				else if (offset == -2)
				{
					tochar("\r\nRunning samp.dll isn't 0.3d version.");
				}else{
					tochar("\n\nCouldn't open ");
					tochar(SAMP_path);
					tochar(" to read what server and nick is the game connected to.");
				}
			}else{
				tochar("Error: Module32First.");
			}
		}else{
			tochar("Error: INVALID_HANDLE_VALUE.");
		}
		CloseHandle(hModuleSnap);
	}else{
		tochar("Error: GTA:SA:MP is not running.");
	}
	chararray[pozice++] = '\0';

	srand((unsigned int)time(NULL));
	FILE* file = fopen("result.test", "wb");
	if (file)
	{
		// encrypting part
		cout << "Test is done. Result file was written to:\r\n" << path << "result.test\r\n\r\n";
	}else{
		cout << "Error: couldn't write to file.\r\n\r\n";
	}
	system("pause");
	return false;
}*/