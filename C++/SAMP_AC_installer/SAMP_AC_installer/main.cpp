#include <windows.h>
#include <stdio.h>
#include <tlhelp32.h>
#include <fstream>
#include "main.h"
 
int PreInstall(char* installation_folder_name) {
 
	return 0;
}
 
int PostInstall (char* installation_folder_name) {
	char value[256];
	DWORD buffer = buf;
 
	HKEY hKey;
	long lError = RegOpenKeyEx(HKEY_CURRENT_USER,
        "Software\\SAMP",
        0,
        KEY_READ,
        &hKey);
 
	DWORD dwRet = RegQueryValueEx(hKey, "gta_sa_exe", NULL, NULL, (LPBYTE) &value, &buffer);
 
	RegCloseKey(hKey);
 
    if(dwRet == ERROR_SUCCESS) {
		//std::ofstream ofile("test.txt");
 
		int i = strlen(value) - 11; // remove \gta_sa.exe
		value[i] = '\0';
		char fpath[256], sourcefile[256];
 
		sprintf_s(fpath, "%s\\d3d9.dll", value);
 
		sprintf_s(sourcefile, "%s\\d3d9.dll", installation_folder_name);
 
		if(MoveFileEx( sourcefile, fpath, MOVEFILE_REPLACE_EXISTING | MOVEFILE_COPY_ALLOWED ) == 0) {
			if(GetLastError() == 5) { 
				if (HWND hWnd = FindWindow(NULL, "GTA:SA:MP")) {
					HandleGTASARunning(hWnd, installation_folder_name);
				} else if(HWND hWnd = FindWindow(NULL, "GTA: San Andreas")) {
					HandleGTASARunning(hWnd, installation_folder_name);
				}
			}
		}
 
		sprintf_s(fpath, "%s\\ac.asi", value);
		sprintf_s( sourcefile, "%s\\ac.asi", installation_folder_name);
 
		MoveFileEx( sourcefile, fpath, MOVEFILE_REPLACE_EXISTING | MOVEFILE_COPY_ALLOWED);
 
		//sprintf_s( sourcefile, "%s\\SAMP_AC.exe", installation_folder_name);
 
		ShellExecute(NULL, "open", "https://www.sixtytiger.com/forum/index.php",
            NULL, NULL, SW_SHOWNORMAL);
 
		return 0;
	} else {
		return 1; // SA-MP not installed
	}
}
 
int PreUninstall (void) {
	char value[256];
	DWORD buffer = buf;
    HKEY hKey;
	long lError = RegOpenKeyEx(HKEY_CURRENT_USER,
        "Software\\SAMP",
        0,
        KEY_READ,
        &hKey);
 
	DWORD dwRet = RegQueryValueEx(hKey, "gta_sa_exe", NULL, NULL, (LPBYTE) &value, &buffer);
 
	RegCloseKey(hKey);
 
    if(dwRet == ERROR_SUCCESS) {
		char fpath[256];
		int i = strlen(value) - 11; // remove \gta_sa.exe
		value[i] = '\0';
		sprintf_s(fpath, "%s\\ac.asi", value);
 
		if(fexist( fpath )) DeleteFile(fpath);
	}
	return 0;
}
 
bool fexist(const char *filename)
{
	std::ifstream dllfile(filename, std::ios::binary);
	return (dllfile == false) ? false : true;
}
 
void HandleGTASARunning(HWND hwnd, char* installation_folder_name) {
 
	int result = MessageBox(NULL, "Whitetiger's Anti-Cheat Cannot install everything when GTA:SA is running, close GTA:SA?", "Whitetiger's Anti-Cheat", MB_YESNO | MB_ICONERROR);
 
	if(result == IDYES) {
		DWORD pId;
		GetWindowThreadProcessId(hwnd, &pId);
		HANDLE process = OpenProcess(PROCESS_TERMINATE, FALSE, pId);
		TerminateProcess(process, 0);
		Sleep(1000);
		PostInstall(installation_folder_name);
	} else {
		result = MessageBox(NULL, "Skipping the last part of the installation may cause problems with running the Anti-Cheat. You should close your GTA:SA.\n\nPressing No will skip the installation for the following files:\n\n* ac.asi\n* vorbisFile.dll\n* vorbisHooked.dll\n\nThe Anti-cheat cannot run without these files installed in your GTA:SA directory, GTA:SA must be closed to install them. You will have to install them manually if you don't press Yes.\n\nYes to install, No to skip.", "Whitetiger's AC", MB_YESNO | MB_ICONSTOP);
 
		if(result == IDYES) {
			DWORD pId;
			GetWindowThreadProcessId(hwnd, &pId);
			HANDLE process = OpenProcess(PROCESS_TERMINATE, FALSE, pId);
			TerminateProcess(process, 0);
			Sleep(1000);
			PostInstall(installation_folder_name);
		} else return;
	}
	return;
}