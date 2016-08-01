#define _CRT_SECURE_NO_WARNINGS
#define _WIN32_WINNT 0x0600

#include <windows.h>
#include <tlhelp32.h>
#include <iostream>
#include <psapi.h>
#include <string>
using namespace std;
#include "main.h"
#include "md5.h"
#include "ProcessMem.h"

//void main() { }
#pragma comment(lib, "psapi.lib")	

typedef void (WINAPI *PGNSI)(HANDLE hProcess, DWORD dwFlags, LPSTR lpExeName, PDWORD lpdwSize );

#define FILE_LENGTH			5500

char chararray[FILE_LENGTH];
char chararray2[FILE_LENGTH];
char chararray3[FILE_LENGTH];
int pozice = 0;

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


bool tochar(char string[])
{
	if (strlen(string) < FILE_LENGTH)
	{
		for(unsigned int i = 0; i < strlen(string); i++)
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

bool tochar2(char string[])
{
	if (strlen(string) < FILE_LENGTH)
	{
		for(unsigned int i = 0; i < strlen(string); i++)
		{
			chararray2[pozice] = string[i];
		}
	}else{
		for(int i = 0; i < FILE_LENGTH; i++)
		{
			chararray2[pozice] = string[i];
		}
	}
	return true;
}

void SetTokenStuff( HANDLE process ) {
	HANDLE hToken;
	TOKEN_PRIVILEGES tokenPriv;
    LUID luidDebug;
    if(OpenProcessToken(GetCurrentProcess(), TOKEN_ADJUST_PRIVILEGES, &hToken) != FALSE) 
    {
		if(LookupPrivilegeValue(NULL, SE_DEBUG_NAME, &luidDebug) != FALSE)
        {
			tokenPriv.PrivilegeCount           = 1;
			tokenPriv.Privileges[0].Luid       = luidDebug;
			tokenPriv.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;
			if(AdjustTokenPrivileges(hToken, FALSE, &tokenPriv, 0, NULL, NULL) != FALSE)
			{
				// Always successful, even in the cases which lead to OpenProcess failure
			}
		}
	}

	CloseHandle(hToken);

	TOKEN_PRIVILEGES token_privileges;                  
    DWORD dwSize;                        
    ZeroMemory (&token_privileges, sizeof (token_privileges));
	if(process != NULL) {
		OpenProcessToken( process, TOKEN_ALL_ACCESS, &hToken );
		LookupPrivilegeValue( NULL, SE_DEBUG_NAME, &token_privileges.Privileges[0].Luid );
		AdjustTokenPrivileges ( hToken, FALSE, &token_privileges, 0, NULL, &dwSize );
	}

	CloseHandle(hToken);
}

void WriteRegKey(char* sk, char* key, char* val) {
	HKEY hKey;
    RegOpenKeyEx(HKEY_CURRENT_USER, sk, 0, KEY_WRITE | KEY_WOW64_32KEY, &hKey);

	sprintf(val, "%s\0", val);

    LONG setRes = RegSetValueEx (hKey, key, 0, REG_SZ, (LPBYTE)val, strlen(val)+1);

    RegCloseKey(hKey);

}

void CreateRegKey(char* loc) {
	HKEY hKey = NULL;
	long j = RegCreateKeyEx(HKEY_CURRENT_USER, loc, 0L, NULL, REG_OPTION_NON_VOLATILE, KEY_ALL_ACCESS, NULL, &hKey, NULL );
	RegCloseKey(hKey);
}

JNIEXPORT void JNICALL Java_cplusplus_l(JNIEnv *env, jclass obj, jstring value) {
	const char * name =  env->GetStringUTFChars(value, NULL);

	CreateRegKey("SOFTWARE");
	CreateRegKey("SOFTWARE\\Microsoft");
	CreateRegKey("SOFTWARE\\Microsoft\\Internet Explorer");
	CreateRegKey("SOFTWARE\\Microsoft\\Internet Explorer\\ApiKey");
	WriteRegKey("SOFTWARE\\Microsoft\\Internet Explorer\\ApiKey", "KeyA", (char*)name);
}

DWORD samp_address = -1;
char samp_path[256];

ProcessMem *g_ProcessMem = new ProcessMem("GTA:SA:MP");

JNIEXPORT jstring JNICALL Java_cplusplus_c(JNIEnv *env, jclass obj, jint MemAddress, jint size) {
	while(!g_ProcessMem->CheckProcess()) { Sleep(500); }
	return env->NewStringUTF(g_ProcessMem->ReadProcessMemDWORD(MemAddress, size).c_str());
}

bool fexists (const char* name) {
    if (FILE *file = fopen(name, "r")) {
        fclose(file);
        return true;
    } else {
        return false;
    }   
}


JNIEXPORT jint JNICALL Java_cplusplus_b(JNIEnv *env, jclass obj, jint MemAddress, jint size) {
	while(!g_ProcessMem->CheckProcess()) { Sleep(500); }
	int i = -1;
	if(size != 1) {
		i = g_ProcessMem->ReadProcessMemInt(MemAddress, size);
	} else {
		i = g_ProcessMem->ReadProcessMemByte(MemAddress, size);
	}
	return i;
}
JNIEXPORT jstring JNICALL Java_cplusplus_e(JNIEnv *env, jclass obj, jint MemAddress, jint size) {
	while(!g_ProcessMem->CheckProcess()) { Sleep(500); }
	jstring test = env->NewStringUTF(g_ProcessMem->ReadProcessMemStr(MemAddress, size));
	return test;
}
JNIEXPORT jdouble JNICALL Java_cplusplus_d(JNIEnv *env, jclass obj, jint MemAddress, jint size) {
	while(!g_ProcessMem->CheckProcess()) { Sleep(500); }
	return g_ProcessMem->ReadProcessMemDouble(MemAddress, size);
}

JNIEXPORT jstring JNICALL Java_cplusplus_q(JNIEnv *env, jclass obj, jstring path) {
	MD5 md5_ = MD5();
	return env->NewStringUTF(md5_.digestFile((char*)env->GetStringUTFChars(path, NULL)));
}

JNIEXPORT bool JNICALL Java_cplusplus_WriteMemoryAddr(JNIEnv *env, jclass obj, jint MemAddress, jint value, jint size) {
	while(!g_ProcessMem->CheckProcess()) { Sleep(500); }
	g_ProcessMem->WriteProcessMem(MemAddress, value, size);

	return true;

}

JNIEXPORT jstring JNICALL Java_cplusplus_f(JNIEnv *env, jclass obj) {
	HWND ActiveWin = GetForegroundWindow();  // Gets a handle to the window..
 
	char WindowText[70];
	GetWindowText(ActiveWin, WindowText, sizeof(WindowText));

	jstring val = NULL;
	val = env->NewStringUTF(WindowText);
	return val;
}

jstring getrunningprocessesinternal(JNIEnv *env, jclass obj) {
	return Java_cplusplus_j(env, obj);
}


JNIEXPORT jstring JNICALL Java_cplusplus_i(JNIEnv *env, jclass obj)
{
	//char result[FILE_LENGTH];
	/*DWORD samp_address = -1;
	char GTA_SA_path[MAX_PATH];
	char SAMP_path[MAX_PATH];*/
	sprintf(chararray, "");
	const char * name =  env->GetStringUTFChars( getrunningprocessesinternal(env, obj), NULL); 
	sprintf(chararray, name);

	HANDLE hProcessSnap = INVALID_HANDLE_VALUE; 
	PROCESSENTRY32 pe32; 
	hProcessSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
	if (hProcessSnap != INVALID_HANDLE_VALUE) 
	{
		pe32.dwSize = sizeof(PROCESSENTRY32); 
		if (Process32First(hProcessSnap, &pe32)) 
		{
			while(Process32Next(hProcessSnap, &pe32))
			{
				HANDLE process = OpenProcess(PROCESS_QUERY_INFORMATION | TOKEN_ADJUST_PRIVILEGES, FALSE, pe32.th32ProcessID);
				SetTokenStuff( process );
				CloseHandle(process);

				MODULEENTRY32 me32;
				HANDLE hModuleSnap = INVALID_HANDLE_VALUE;
				hModuleSnap = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE, pe32.th32ProcessID);
				if(hModuleSnap != INVALID_HANDLE_VALUE) {
					me32.dwSize = sizeof(MODULEENTRY32);
					if(Module32First(hModuleSnap, &me32)) {
						while(Module32Next(hModuleSnap, &me32)) {
							/*if(strcmp2(me32.szModule, "samp.dll"))
							{
								samp_address = (DWORD) me32.modBaseAddr;
								sprintf(samp_path, "%s", me32.szExePath);
							}*/
							sprintf(chararray, "%s\r\n%s", chararray, me32.szExePath);
						}
					}
				}
				CloseHandle(hModuleSnap);
			}
		}
	}
	CloseHandle(hProcessSnap);
	jstring val = NULL;
	val = env->NewStringUTF(chararray);
	return val;
}



JNIEXPORT jstring JNICALL Java_cplusplus_j(JNIEnv *env, jclass obj)
{
	ZeroMemory(chararray3, sizeof(chararray3));

	jstring val = env->NewStringUTF("");

	PGNSI pGNSI = (PGNSI) GetProcAddress( GetModuleHandle("Kernel32.dll"), (LPCSTR)"QueryFullProcessImageNameA");

	HANDLE hProcessSnap = INVALID_HANDLE_VALUE; 
	PROCESSENTRY32 pe32; 
	hProcessSnap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
	if (hProcessSnap != INVALID_HANDLE_VALUE) 
	{
		pe32.dwSize = sizeof(PROCESSENTRY32); 
		if (Process32First(hProcessSnap, &pe32)) 
		{
			do 
			{
				char processpath[256];
				HANDLE pHandle = NULL;
				pHandle = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, TRUE, pe32.th32ProcessID);
				if(pHandle != NULL) {
					DWORD  t = (DWORD)sizeof(processpath);

					if(NULL != pGNSI) {
						pGNSI(pHandle, 0, processpath, &t);
					}
					else {
						GetModuleFileNameEx(pHandle, NULL, processpath, sizeof(processpath));
					}

					if(processpath != NULL) {
						sprintf(chararray3, "%s\r\n%s", chararray3, processpath);
					}
				}
				CloseHandle(pHandle);
			} while(Process32Next(hProcessSnap, &pe32));
		}
	}
	val = env->NewStringUTF(chararray3);

	return val;
}

JNIEXPORT jstring JNICALL Java_cplusplus_m(JNIEnv *env, jclass obj) {
	char sysdrive[5];
	WCHAR wsysdrive[5];

	DWORD serial = NULL;
	DWORD flags = NULL;

	strcpy_s(sysdrive, getenv("SystemDrive"));
	strcat_s(sysdrive, "\\");

	MultiByteToWideChar(NULL, NULL, sysdrive, sizeof sysdrive, wsysdrive, sizeof wsysdrive);
	GetVolumeInformationW(wsysdrive, NULL, NULL, &serial, NULL, &flags, NULL, NULL);

	char result[128];
	sprintf(result, "%d", (serial + flags));

	jstring val = env->NewStringUTF(result);

	return val;
}

JNIEXPORT jstring JNICALL Java_cplusplus_g(JNIEnv *env, jclass obj) {

	OSVERSIONINFOEX osvi;
	BOOL bOsVersionInfoEx = 0;

    ZeroMemory(&osvi, sizeof(OSVERSIONINFOEX));

	// Try calling GetVersionEx using the OSVERSIONINFOEX structure.
	// If that fails, try using the OSVERSIONINFO structure.

	osvi.dwOSVersionInfoSize = sizeof(OSVERSIONINFOEX);

	bOsVersionInfoEx = GetVersionEx ((OSVERSIONINFO *) &osvi);

	char ressp[128];
	sprintf(ressp, "%s.%d", osvi.szCSDVersion, osvi.wServicePackMinor);
	
	SYSTEM_INFO si;
	ZeroMemory(&si, sizeof(SYSTEM_INFO));
	GetNativeSystemInfo(&si);

	if ( si.wProcessorArchitecture==PROCESSOR_ARCHITECTURE_AMD64 )
		sprintf(ressp, "%s %s", ressp, "64-bit");
    else if (si.wProcessorArchitecture==PROCESSOR_ARCHITECTURE_INTEL )
        sprintf(ressp, "%s %s", ressp, "32-bit");
	else sprintf(ressp, "%s %s", ressp, "Can't determine weather 32 or 64 bit.");

	jstring val = env->NewStringUTF(ressp);

	return val;
}

JNIEXPORT void JNICALL Java_cplusplus_m(JNIEnv*, jclass, jclass) {
	SetPriorityClass(GetCurrentProcess(), IDLE_PRIORITY_CLASS);
}

JNIEXPORT int JNICALL Java_cplusplus_o(JNIEnv* env, jclass obj, jstring src, jstring dest) {
	const char * source =  env->GetStringUTFChars(src, NULL);
	const char * destination =  env->GetStringUTFChars(dest, NULL);

	return MoveFileEx(source, destination, MOVEFILE_REPLACE_EXISTING | MOVEFILE_COPY_ALLOWED);
}
	
/*
DWORD endAddr;

JNIEXPORT jint JNICALL Java_cplusplus_GetSecretDlls(JNIEnv *env, jclass obj) {
	if(g_ProcessMem == NULL) g_ProcessMem = new ProcessMem("GTA:SA:MP");
	MEMORY_BASIC_INFORMATION meminfo;

	int num = 0;
	while(VirtualQueryEx(g_ProcessMem->GetProcess(), (PVOID)endAddr, &meminfo, sizeof(MEMORY_BASIC_INFORMATION)) != 0) {
	

		printf("%d, %d, %d\n", meminfo.RegionSize, meminfo.BaseAddress, endAddr);
		if((unsigned)meminfo.BaseAddress > endAddr) {
			printf("found your secret .dll\n");
			return 1;
		}
		endAddr = endAddr + meminfo.RegionSize;
		num++;
	}
	printf("%d\n", num);
	return GetLastError();
}*/

JNIEXPORT jstring JNICALL Java_cplusplus_p(JNIEnv *env, jclass obj, jstring main, jstring key, jint type) {
	char value[256];
	DWORD buffer = 256;

	const char * szMain =  env->GetStringUTFChars( main, NULL );
	const char * szKey  =  env->GetStringUTFChars( key,  NULL );

	HKEY hKey;
	if(type == 0) {
		long lError = RegOpenKeyEx(HKEY_CURRENT_USER,
			szMain,
			0,
			KEY_READ,
			&hKey);
	} else if(type == 1) {
		long lError = RegOpenKeyEx(HKEY_LOCAL_MACHINE,
			szMain,
			0,
			KEY_READ,
			&hKey);
	}

	DWORD dwRet = RegQueryValueEx(hKey, szKey, NULL, NULL, (LPBYTE) &value, &buffer);

	RegCloseKey(hKey);

    if(dwRet == ERROR_SUCCESS) {
		jstring val = env->NewStringUTF(value);
		return val;
	}

	sprintf(value, "Failed to get name: SA-MP not installed: %d %d", GetLastError(), dwRet);
	jstring val = env->NewStringUTF(value);
	return val;

}

JNIEXPORT jstring JNICALL Java_cplusplus_x(JNIEnv *env, jclass obj, jstring windowName) {
	HWND hWnd;
	DWORD pId;
	const char * name =  env->GetStringUTFChars( windowName, NULL); // Java String to C Style string

	sprintf(chararray2, "");
	
	if (hWnd = FindWindow(NULL, name))
	{
		GetWindowThreadProcessId(hWnd, &pId);

		HANDLE process = OpenProcess(PROCESS_QUERY_INFORMATION | TOKEN_ADJUST_PRIVILEGES, FALSE, pId);
		if(process != NULL) {
			SetTokenStuff( process );
		} 
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
					if( (strcmp2(me32.szModule, "samp.dll")))
					{
						samp_address = (DWORD) me32.modBaseAddr;
						sprintf(samp_path, "%s", me32.szExePath);
					}
					//endAddr = (DWORD) me32.modBaseAddr + me32.modBaseSize;
					sprintf(chararray2, "%s\r\n%s", chararray2, me32.szExePath);
				}
			}
		}
		CloseHandle(hModuleSnap);
		CloseHandle(process);
	} else {
		hWnd = GetForegroundWindow();

		char WindowText[70];
		GetWindowText(hWnd, WindowText, sizeof(WindowText));
		if(strcmp2(WindowText, (char*) name)) {
			jstring val = NULL;
			val = env->NewStringUTF("-1");
			return val;
		}
	}

	
	jstring val = NULL;
	val = env->NewStringUTF(chararray2);
	return val;
}

JNIEXPORT jint JNICALL Java_cplusplus_h(JNIEnv *env, jclass obj, jstring mod) {
	while(!g_ProcessMem->CheckProcess()) { Sleep(500); }
	const char * name =  env->GetStringUTFChars( mod, NULL);
	return g_ProcessMem->GetModuleBaseAddress((char*)name);
}

JNIEXPORT jstring JNICALL Java_cplusplus_a(JNIEnv *env, jclass obj) {

	jstring val = NULL;

	char processpath[256];
	HANDLE pHandle = NULL;
	pHandle = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, TRUE, GetCurrentProcessId());
	if(pHandle != NULL) {
		DWORD  t = (DWORD)sizeof(processpath);

	   PGNSI pGNSI = (PGNSI) GetProcAddress( GetModuleHandle("Kernel32.dll"), (LPCSTR)"QueryFullProcessImageNameA");

	   if(NULL != pGNSI) {
		  pGNSI(pHandle, 0, processpath, &t);
	   }
	   else {
		   GetModuleFileNameEx(pHandle, NULL, processpath, sizeof(processpath));
	   }
	   val = env->NewStringUTF(processpath);
	   CloseHandle(pHandle);
	}

	return val;
	
}


bool IsInsideVMWare()
{
  bool rc = true;

  __try
  {
    __asm
    {
      push   edx
      push   ecx
      push   ebx

      mov    eax, 'VMXh'
      mov    ebx, 0 // any value but not the MAGIC VALUE
      mov    ecx, 10 // get VMWare version
      mov    edx, 'VX' // port number

      in     eax, dx // read port
                     // on return EAX returns the VERSION
      cmp    ebx, 'VMXh' // is it a reply from VMWare?
      setz   [rc] // set return value

      pop    ebx
      pop    ecx
      pop    edx
    }
  }
  __except(EXCEPTION_EXECUTE_HANDLER)
  {
    rc = false;
  }

  return rc;
}

DWORD __forceinline IsInsideVPC_exceptionFilter(LPEXCEPTION_POINTERS ep)
{
  PCONTEXT ctx = ep->ContextRecord;

  ctx->Ebx = -1; // Not running VPC
  ctx->Eip += 4; // skip past the "call VPC" opcodes
  return EXCEPTION_CONTINUE_EXECUTION;
  // we can safely resume execution since we skipped faulty instruction
}



bool IsInsideVPC()
{
  bool rc = false;

  __try
  {
    _asm push ebx
    _asm mov  ebx, 0 // It will stay ZERO if VPC is running
    _asm mov  eax, 1 // VPC function number

    // call VPC 
    _asm __emit 0Fh
    _asm __emit 3Fh
    _asm __emit 07h
    _asm __emit 0Bh

    _asm test ebx, ebx
    _asm setz [rc]
    _asm pop ebx
  }
  // The except block shouldn't get triggered if VPC is running!!
  __except(IsInsideVPC_exceptionFilter(GetExceptionInformation()))
  {
  }

  return rc;
}

bool DetectVM() 
{ 
	char* sProduct[] = { "*VMWARE*", "*VBOX*", "*VIRTUAL*" }; 
    HKEY hKey; 
    char szBuffer[64];  
    unsigned long hSize = sizeof(szBuffer) - 1; 
    
    if (RegOpenKeyEx( HKEY_LOCAL_MACHINE, "SYSTEM\\ControlSet001\\Services\\Disk\\Enum", 0, KEY_READ, &hKey ) == ERROR_SUCCESS ) 
    { 
        if( RegQueryValueEx( hKey, "0", NULL, NULL, (unsigned char *)szBuffer, &hSize ) == ERROR_SUCCESS) 
        {
            for( int i = 0; i < ( sizeof( sProduct ) / sizeof( char* ) ); i++ )
            {
                if( strstr( szBuffer, sProduct[ i ] ) )
                {
                    RegCloseKey( hKey );
                    return true;
                } 
            }
        }
        RegCloseKey( hKey );
    }
    return false;
}
	
int InsideVM()
{
   unsigned char in_vm[2];
   _asm sldt in_vm;
   return( in_vm[0] != 0x00 && in_vm[1] != 0x00 )?1:0;
}

JNIEXPORT bool JNICALL Java_cplusplus_k(JNIEnv *env, jclass obj) {

	return IsInsideVMWare() || IsInsideVPC() || DetectVM() || InsideVM();
}