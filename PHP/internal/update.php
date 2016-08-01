<?php
	require_once 'connect.php';

	function check_update() {
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		if($result = $uConnect->query("SELECT `Version`, `Link`, `Link_InstallerUpdate`, `MD5` FROM `AC_Info` WHERE `Type` = 'Client' LIMIT 1")) {
			while($row = $result->fetch_assoc()) {
				if((strcmp($_POST['ver'], $row['Version']) == 0 && strlen($_POST['ver']) != 0) || strcmp($row['MD5'], $_POST['APIKey']) == 0) {
					echo 'Already up to date!';
				} else {
					echo $row['Version'] . ' ' . $row['Link_InstallerUpdate'];
				}
			}
			$result->free();
		}
		$uConnect->close();
	}
	
	check_update();
	exit();
?>