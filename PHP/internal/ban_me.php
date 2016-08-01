<?php
	require_once 'connect.php';
	require_once 'key.php';

	function begin() {
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
			
		$hardwareid = $uConnect->real_escape_string($_POST['HardwareID']);
		$hardwareid2 = $uConnect->real_escape_string($_POST['HardwareID2']);
		$hardwareid3 = "INVALID HARDWAREID 3";
		$hardwareid4 = "INVALID HARDWAREID 4";
		$volume = $uConnect->real_escape_string($_POST['Volume']);
		$ip = $uConnect->real_escape_string($_POST['IP']);
		$name = $uConnect->real_escape_string($_POST['Name']);
		$realreason = $uConnect->real_escape_string($_POST['reason']);
		
				
		if($result = $uConnect->query("SELECT * FROM `AC_Bans` WHERE (`HardwareID` = '" . $hardwareid . "' OR `HardwareID2` = '" . $hardwareid2 . "' OR `Volume` = '" . $volume . "' OR `IP` = '" . $ip . "') AND `Timestamp` = 0")) {
			if($result->num_rows == 0) {
				$uConnect->query("INSERT INTO `AC_Bans`(`HardwareID`, `HardwareID2`, `HardwareID3`, `HardwareID4`, `Volume`, `IP`, `Name`, `Reason`, `Timestamp`) VALUES ('" . $hardwareid . "', '" . $hardwareid2 . "', '" . $hardwareid3 . "', '" . $hardwareid4 . "', '" . $volume . "', '" . $ip . "', '" . $name . "', '" . $realreason . "')");
			}
		} else {
			$uConnect->query("INSERT INTO `AC_Bans`(`HardwareID`, `HardwareID2`, `HardwareID3`, `HardwareID4`, `Volume`, `IP`, `Name`, `Reason`, `Timestamp`) VALUES ('" . $hardwareid . "', '" . $hardwareid2 . "', '" . $hardwareid3 . "', '" . $hardwareid4 . "', '" . $volume . "', '" . $ip . "', '" . $name . "', '" . $realreason . "')");
		}
		
		$result->free();
		$uConnect->close();
		exit();
		return 1;
	}
?>
