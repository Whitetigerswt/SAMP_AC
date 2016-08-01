<?php
	
	require_once 'connect.php';
	function begin() {
		
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		$EscIP = $uConnect->real_escape_string($_POST['IP']);
		$x = $uConnect->real_escape_string($_POST['x']);
		$y = $uConnect->real_escape_string($_POST['y']);
		$z = $uConnect->real_escape_string($_POST['z']);

		if(strlen($x) > 4) $uConnect->query("UPDATE AC_Players SET `AppRunning` = 1, `NextCheck` = (UNIX_TIMESTAMP() + 80), `X` = '" . $x . "', `Y` = '" . $y . "', `Z` = '" . $z . "' WHERE IP = '" . $EscIP . "'");
		else $uConnect->query("UPDATE AC_Players SET `AppRunning` = 1, `NextCheck` = (UNIX_TIMESTAMP() + 80) WHERE IP = '" . $EscIP . "'");	

		$uConnect->close();
		
		echo 'OK';
	}

	begin();
	exit();
?>
