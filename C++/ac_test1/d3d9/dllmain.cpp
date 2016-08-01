// dllmain.cpp : Defines the entry point for the DLL application.
#include "stdafx.h"

BOOL APIENTRY DllMain( HMODULE hModule,
                       DWORD  ul_reason_for_call,
                       LPVOID lpReserved
					 )
{
	switch (ul_reason_for_call)
	{
	case DLL_PROCESS_ATTACH:
		char buffer[MAX_PATH];
    
		::GetSystemDirectory(buffer,MAX_PATH);

		strcat_s(buffer,"\\d3d9.dll");
		LoadLibraryA(buffer);
		break;
	}
	return TRUE;
}

