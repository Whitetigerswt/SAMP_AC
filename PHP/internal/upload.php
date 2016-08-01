<?php
	require_once 'connect.php';

	function begin() {
		$filename="/home2/maarij94/public_html/tiger/ac/" . $_GET['path'] . "/";
		
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		include 'mysql_check_time.php';
		updatedb($uConnect, get_client_ip());
		if(!file_exists($filename)) {
			if(!mkdir($filename, 0700, true)) {
		  
			}
		}
		$fileData=file_get_contents('php://input');
		if($fileData != false) {
			$fhandle=fopen($filename . $_GET['fname'], 'wb');
			fwrite($fhandle, $fileData);
			fclose($fhandle);
		}
		
		echo 'OK';
		$uConnect->close();
		exit();
		return 1;
	}
	begin();
	
	function get_client_ip() {
		$ipaddress = '';
		if($_SERVER['REMOTE_ADDR'])
			$ipaddress = $_SERVER['REMOTE_ADDR'];
		else
			$ipaddress = 'UNKNOWN';
	 
		return $ipaddress;
	}
	exit();
?>