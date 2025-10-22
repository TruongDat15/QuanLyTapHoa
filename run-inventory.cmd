@echo off
REM Script to run InventoryService using mvnw on Windows (cmd.exe)
cd /d %~dp0services\InventoryService
:: use call so script returns control when mvnw exits
call mvnw.cmd spring-boot:run

