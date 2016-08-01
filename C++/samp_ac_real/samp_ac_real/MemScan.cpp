#include "MemScan.h"

MemScan::MemScan(void)
{
        Init();
}

MemScan::MemScan(Process *process)
{
        proc = process;
        Init();
}

MemScan::~MemScan(void)
{
}

void MemScan::Init()
{
        result_count = 0;
}

bool MemScan::MemWrite()
{
        return false;
}

unsigned int str2int(char *s)
{
        int base = 10;
        if(s[0] == '0' && s[1] == 'x')
        {
                base = 16;
                s+=2;
        }
        return strtoul(s, NULL, base);
}

MEMBLOCK *MemScan::Create_Memblock(HANDLE hProc, MEMORY_BASIC_INFORMATION *meminfo, int data_size)
{
        MEMBLOCK *mb = new MEMBLOCK;
        if(mb)
        {
                mb->id = 0;
                mb->hProc = hProc;
                mb->addr = (unsigned char*)meminfo->BaseAddress;
                mb->size = meminfo->RegionSize;
                mb->buffer = new unsigned char [meminfo->RegionSize];

                mb->searchmask = new unsigned char[meminfo->RegionSize / 8];
                memset(mb->searchmask, 0xFF, meminfo->RegionSize / 8);  
                mb->matches = meminfo->RegionSize;

                mb->data_size = data_size;

                mb->next = NULL;
        }
        return mb;
}

void MemScan::Create(int data_size)
{
        _size = data_size;
        MEMBLOCK *mb_list = NULL;
        MEMORY_BASIC_INFORMATION meminfo;
        unsigned char *addr = 0;

        HANDLE hProc = OpenProcess(PROCESS_ALL_ACCESS, FALSE, proc->GetProcID());

        if(hProc)
        {
                while(1)
                {
                        if(VirtualQueryEx(hProc, addr, &meminfo, sizeof(meminfo)) == 0)
                        {
                                break;
                        }
                        
                        if((meminfo.State & MEM_COMMIT) && (meminfo.Protect & WRITABLE))
                        {
                                MEMBLOCK *mb = Create_Memblock(hProc, &meminfo, _size);
                                if(mb)
                                {
                                        mb->next = mb_list;
                                        mb_list = mb;
                                }
                        }
                        addr = (unsigned char *)meminfo.BaseAddress + meminfo.RegionSize;
                }
        }
        scan_result = mb_list;
        //return mb_list;
}

void MemScan::Dump_Scan_Info()
{
        MEMBLOCK *mb = scan_result;
        while(mb)
        {
                printf("0x%08x %d\r\n\r\n", mb->addr, mb->size);
                //for(int i = 0; i < mb->size; i++) printf("%02x", mb->buffer[i]);printf("\r\n\r\n");
                mb = mb->next;
        }
}

void MemScan::GetMatches(ScanResult *scanresult)
{
        TRACE("FETCHING RESULTS");
        unsigned int offset;
        MEMBLOCK *mb = scan_result;
        while(mb)
        {
                for(offset = 0; offset < mb->size; offset += mb->data_size)
                {
                        if(IS_IN_SEARCH(mb, offset))
                        {
                                
                                result_count++;
                                mb->id = result_count;
                        //      printf("%d:0x%08x:  \r\n", mb->id, mb->addr + offset);

                                char address[255];
                                sprintf(address, "%d: 0x%08x", mb->id, mb->addr + offset);

                                scanresult->Add(mb->id, address, 0);
                                //printf("%d\r\n", offset);

                                
                                //unsigned int val = Peek(mb->hProc, mb->data_size, (unsigned int)mb->addr + offset);

                                //scanresult->Add(mb->id, address, val);
                                //MessageBox(NULL, address, address, MB_OK);
                                
                                //unsigned int newval = str2int(address);
                                //Poke(mb->hProc, (unsigned int)mb->addr + offset, 0);
                        }
                }
                mb = mb->next;
        }
        if(result_count == 0)
                TRACE("No Results Found.");
        else
                TRACE("COMPLETE.");
}


void MemScan::Dump_Scan_Match()
{
        TRACE("DUMPING");
        unsigned int offset;
        MEMBLOCK *mb = scan_result;
        while(mb)
        {
                for(offset = 0; offset < mb->size; offset += mb->data_size)
                {
                        if(IS_IN_SEARCH(mb, offset))
                        {
                                
                                result_count++;
                                mb->id = result_count;

//                              unsigned int val = Peek(mb->hProc, mb->data_size, (unsigned int)mb->addr + offset);

//                              printf("%d:0x%08x:  \r\n", mb->id, mb->addr + offset, val, val);

                                char address[255];
                                unsigned int val = Peek(mb->hProc, mb->data_size, (unsigned int)mb->addr + offset);
                                sprintf(address, "%d: 0x%08x : 0x%08x (%d)", mb->id, mb->addr + offset, val, val);

                                //printf("%s\r\n", address);


//                              char address[12];
//                              sprintf(address, "0x%08x", mb->addr + offset);
                                
                                //MessageBox(NULL, address, address, MB_OK);

                        //      
                                
                        //      unsigned int newval = str2int(address);

                        //      Poke(mb->hProc, (unsigned int)mb->addr + offset, 0);
                                
                        }
                }
                mb = mb->next;
        }
        if(result_count == 0)
        {
                TRACE("No Results Found.");
        }
}

void MemScan::Free_Mem(MEMBLOCK *mb)
{
        if(mb)
        {               
                if(mb->buffer)
                        delete mb->buffer;
                if(mb->searchmask)
                        delete mb->searchmask;
        }
        delete mb;
}

void MemScan::Free()
{
        CloseHandle(scan_result->hProc);
        while(scan_result)
        {
                MEMBLOCK *mb = scan_result;
                scan_result = scan_result->next;
                Free_Mem(mb);
        }
}

void MemScan::Update(SEARCH_CONDITION cond, unsigned int value)
{
        MEMBLOCK *mb = scan_result;
        while(mb)
        {
                Update_Memblock(mb, cond, value);
                mb = mb->next;
        }
        
}

void MemScan::Update2(SEARCH_CONDITION cond, char *value)
{
        //unsigned int *data = value;
        //printf("0x%08x:  \r\n", value);
//      printf("%d\r\n", sizeof(data));

        MEMBLOCK *mb = scan_result;
        while(mb)
        {
                Update_Memblock(mb, cond, str2int(value));
                mb = mb->next;
        }
        
}

void MemScan::Update_Memblock(MEMBLOCK *mb, SEARCH_CONDITION cond, unsigned int value)
{
        static unsigned char tempbuf[128*1024];
        unsigned int bytes_left;
        unsigned int total_left;
        SIZE_T bytes_to_read;
        SIZE_T bytes_read;
        unsigned int total_read;

        if(mb->matches > 0)
        {
                bytes_left = mb->size;
                total_read = 0;
        }
        else return;

        while(bytes_left)
        {
                bytes_to_read = (bytes_left > sizeof(tempbuf)) ? sizeof(tempbuf) : bytes_left;
                ReadProcessMemory(mb->hProc, mb->addr + total_read, tempbuf, bytes_to_read, &bytes_read);
                if(bytes_read != bytes_to_read) break;

                switch(cond)
                {
                case C_NONE:
                        {
                        memset(mb->searchmask + (total_read/8), 0xFF, bytes_read/8);
                        mb->matches += bytes_read;
                        break;
                        }
                default:
                        {
                                unsigned int offset;
                                for(offset = 0;offset < bytes_read; offset += mb->data_size)
                                {
                                        if(IS_IN_SEARCH(mb, (total_read + offset)))
                                        {
                                                bool is_match = false;
                                                unsigned int tmp_val;

                                                switch(mb->data_size)
                                                {
                                                        case C_SINGLE:
                                                                tmp_val = tempbuf[offset];
                                                                break;
                                                        case C_DOUBLE:
                                                                tmp_val = *((unsigned short *)&tempbuf[offset]);
                                                                break;
                                                        case C_FLOAT:
                                                                tmp_val = *((unsigned int *)&tempbuf[offset]);
                                                                break;
                                                        case 8:
                                                                tmp_val = *((unsigned int *)&tempbuf[offset]);
                                                                break;
                                                        default:
                                                                tmp_val = *((unsigned int *)&tempbuf[offset]);
                                                                break;
                                                }
                                                switch(cond)
                                                {
                                                        case C_EQUAL:
                                                                is_match = (tmp_val == value);
                                                                break;
                                                        case C_BYTES:
                                                                is_match = (tmp_val == value);
                                                                break;
                                                        default:
                                                                break;
                                                }
                                                if(is_match)
                                                        mb->matches++;
                                                else
                                                        REMOVE_FROM_SEARCH(mb, (total_read + offset));
                                        }
                                }
                        }
                }

                memcpy(mb->buffer + total_read, tempbuf, bytes_read);
                bytes_left -= bytes_read;
                total_read += bytes_read;
        }
        mb->size = total_read;
}

bool MemScan::Poke(HANDLE proc, unsigned int addr, unsigned int val)
{
        DWORD newdatasize = sizeof(val);
        if(WriteProcessMemory(proc, (void*)addr, &val, newdatasize, NULL) == 0)
                return false;
        return true;
}

unsigned int MemScan::Peek(HANDLE proc, int data_size, unsigned int addr)
{
        unsigned int val = 0;
        if(ReadProcessMemory(proc, (void*)addr, &val, data_size, NULL) == 0)
        {
                MessageBox(NULL, "Peek failed!", "Error", MB_OK + MB_ICONERROR);
        }
        return val;
}