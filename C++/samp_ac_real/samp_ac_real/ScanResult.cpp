#include "ScanResult.h"

ScanResult::ScanResult(void)
{
}

ScanResult::~ScanResult(void)
{
}

void ScanResult::Debug()
{
        for (std::list<RESULT *>::iterator it = results.begin(); it != results.end(); it++)
        {
                printf("%s\r\n", (*it)->addr);
        }
        Free();
}

void ScanResult::Add(int id, char *addr, unsigned int value)
{
        RESULT *result = new RESULT;
        {
                result->id = id;

                result->addr = new char[strlen(addr)];
                memset(result->addr, 0, strlen(addr));
                strcpy(result->addr, addr);

                result->value = value;
                result->next = NULL;
        }
        results.push_back(result);
}

void ScanResult::Free()
{
        for (std::list<RESULT *>::iterator it = results.begin(); it != results.end(); it++)
        {
                //delete (*it)->addr;
                delete (*it);
        }
}
