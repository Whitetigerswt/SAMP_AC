<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<title>Whitetiger's Anti-Cheat</title>
<meta http-equiv="REFRESH" content="0;url=https://www.sixtytiger.com/tiger/ac_files/samp_ac_1.87.zip">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
<!--
body {
	background-color: #999999;
}
-->
</style></HEAD>
<BODY>
<div align="center">
  <p><strong>A</strong>nti-<strong>C</strong>heat Download Page</p>
  <p><img src="https://sixtytiger.com/tiger/ac_files/AC.png" alt="ANTICHEAT DOWNLOAD" width="152" height="152" border="0"></a></p>
  <font size="3" color="red"><strong>DO NOT TEST CHEATS. THE ANTI-CHEAT RELLY WORKS AND IT REALLY WILL BAN YOU PERMANENTLY. YOU WON'T BE UNBANNED FOR TESTING CHEATS</font></strong>
  <p><em><strong>Thank you for playing fair! </strong></em></p>
  <p><a href="samp_ac_1.87.zip">Click here if the download doesn't start.</a></p>
  <p>&nbsp;</p>

</div>

</BODY>

</HTML>

<?php


	$file = fopen("downloads.txt", "r+") or exit("Unable to open file!");
	//Output a line of the file until the end is reached
	if(!feof($file))
	{
		echo '<div align="center">' . strlen(fgets($file)) . ' Downloads</div>';
	}
	$contents = intval(str_replace(" ", "", fgets($file))) + 1;
	fwrite($file, "" . $contents );
	fclose($file);

	$file = 'samp_ac_1.87.zip';
	echo '<div align="center">MD5: ' . md5_file($file) . '<br>SHA1: ' . sha1_file($file) . '</div>';
?>
