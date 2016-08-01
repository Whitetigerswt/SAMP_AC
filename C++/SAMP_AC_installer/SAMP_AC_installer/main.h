extern "C" __declspec( dllexport ) int PreInstall (char* installation_folder_name);
extern "C" __declspec( dllexport ) int PostInstall (char* installation_folder_name);
extern "C" __declspec( dllexport ) int PreUninstall (void); 

bool fexist(const char *filename);
void runCmdLine(char* installation_folder_name);
void HandleGTASARunning(HWND hwnd, char* installation_folder_name);

#define buf 500
