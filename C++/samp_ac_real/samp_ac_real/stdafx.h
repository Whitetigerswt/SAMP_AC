//  Curry
//  Copyright (C) 2001 Mike Jackman, Tim Rennie, Leonardo Zide
//
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Lesser General Public
//  License as published by the Free Software Foundation; either
//  version 2.1 of the License, or (at your option) any later version.
//
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Lesser General Public License for more details.
//
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-


// stdafx.h : include file for standard system include files,
//  or project specific include files that are used frequently, but
//      are changed infrequently
//

#if !defined(AFX_STDAFX_H__214D7491_03C8_444D_AAEB_73A8A21B84CB__INCLUDED_)
#define AFX_STDAFX_H__214D7491_03C8_444D_AAEB_73A8A21B84CB__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifdef WIN32
#pragma warning(disable : 4786)
#pragma warning(disable : 4290)
#endif

#include <gdk/gdkkeysyms.h>
#include <gtk/gtk.h>
#include <stdio.h>
#include <stdlib.h>

#ifdef __linux__

#include <GL/glx.h>

typedef void* HMODULE;
typedef void* LPVOID;
typedef int BOOL;
typedef char* LPCTSTR;

#define MB_OK                       0x00000000L
#define MB_OKCANCEL                 0x00000001L
#define MB_ABORTRETRYIGNORE         0x00000002L
#define MB_YESNOCANCEL              0x00000003L
#define MB_YESNO                    0x00000004L
#define MB_RETRYCANCEL              0x00000005L
 
 
#define MB_ICONHAND                 0x00000010L
#define MB_ICONQUESTION             0x00000020L
#define MB_ICONEXCLAMATION          0x00000030L
#define MB_ICONASTERISK             0x00000040L
 
#define MB_USERICON                 0x00000080L
#define MB_ICONWARNING              MB_ICONEXCLAMATION
#define MB_ICONERROR                MB_ICONHAND
#define MB_ICONINFORMATION          MB_ICONASTERISK
#define MB_ICONSTOP                 MB_ICONHAND
 
#define MB_TYPEMASK                 0x0000000FL
#define MB_ICONMASK                 0x000000F0L
#define MB_DEFMASK                  0x00000F00L
#define MB_MODEMASK                 0x00003000L
#define MB_MISCMASK                 0x0000C000L
 
#define IDOK                1
#define IDCANCEL            2
#define IDABORT             3
#define IDRETRY             4
#define IDIGNORE            5
#define IDYES               6
#define IDNO                7

#define WINAPI
#define APIENTRY

#ifndef GUID_DEFINED
#define GUID_DEFINED
typedef struct _GUID
{
  unsigned long  Data1;
  unsigned short Data2;
  unsigned short Data3;
  unsigned char  Data4[8];
} GUID;
#endif
 
#if defined(__cplusplus)
#ifndef _REFGUID_DEFINED
#define _REFGUID_DEFINED
#define REFGUID             const GUID &
#endif // !_REFGUID_DEFINED
#endif

typedef struct tagRECT
{
    long    left;
    long    top;
    long    right;
    long    bottom;
} RECT, *PRECT, *LPRECT;

#endif // __linux__

#include "qerplugin.h"
//#include "mathlib.h"
#include "igl.h"
#include "iselectedface.h"
#include "isurfaceplugin.h"
#include "ishaders.h"
#include "iui.h"
#include "iui_gtk.h"
#include "iepairs.h"
#include <string>

extern _QERFuncTable_1			g_FuncTable;
extern _QERShadersTable		  g_ShadersTable;
extern _QERQglTable			    g_QglTable;
extern _QERUIGtkTable       g_UIGtkTable;
extern _QEREpairsTable         g_EpairTable;

extern std::string g_strBasePath;

#define Sys_Printf g_FuncTable.m_pfnSysPrintf
#define Sys_FPrintf g_FuncTable.m_pfnSysFPrintf

#include "CurryApp.h"
extern CCurryApp				theApp;

#define ID_INSERTSHADERFILE 10000
#define ID_INSERTSHADER		10001
#define ID_EDITSHADER		10002
#define ID_DELETESHADER		10003

#define ID_3DVIEW			10004
#define ID_2DVIEW			10005
#define ID_BACKCOLOR		10006


// TODO: reference additional headers your program requires here

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_STDAFX_H__214D7491_03C8_444D_AAEB_73A8A21B84CB__INCLUDED_)