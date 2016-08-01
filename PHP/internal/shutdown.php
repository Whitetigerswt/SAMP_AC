<?php
	
	require_once 'key.php';
	require_once 'connect.php';
		
	function begin() {
		require_once 'connect.php';
		
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		$ip = $uConnect->real_escape_string($_POST['IP']);
		$uConnect->query("UPDATE `AC_Players` SET `AppRunning` = 0 WHERE `IP` = '" . $ip . "'");
				
		$uConnect->close();
		echo 'OK';
		exit();
	}
	exit();
	
?>
