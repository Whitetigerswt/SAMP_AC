<?php
	
	require_once 'key.php';
	require_once 'connect.php';
		
	function begin() {
		
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		$player = $uConnect->real_escape_string($_POST['Player']);
		$connectedip = $uConnect->real_escape_string($_POST['ConnectedIP']);
		$ip = $uConnect->real_escape_string($_POST['IP']);
		$hardwareid = $uConnect->real_escape_string($_POST['HardwareID']);
		$hardwareid2 = $uConnect->real_escape_string($_POST['HardwareID2']);
		$hardwareid3 = $uConnect->real_escape_string($_POST['HardwareID3']);
		$hardwareid4 = $uConnect->real_escape_string($_POST['HardwareID4']);
		$volume = $uConnect->real_escape_string($_POST['Volume']);
		$gpci = $uConnect->real_escape_string($_POST['gpci']);
		
		
		if($result = $uConnect->query("SELECT * FROM `AC_Players` WHERE `IP` = '" . $ip . "' AND `HardwareID` = '" . $hardwareid . "' AND `HardwareID2` = '" . $hardwareid2 . "' ")) {
			$row = $result->fetch_assoc();
			if(strlen($row['file_0']) > 0) {
				$uConnect->query("UPDATE `AC_Players` SET `Player` = '" . $player . "', `ConnectedIP` = '" . $connectedip . "', file_0 = '0', file_1 = '0', file_2 = '0', file_3 = '0', file_4 = '0', file_5 = '0', file_6 = '0', file_7 = '0', file_8 = '0', file_9 = '0', file_10 = '0', file_11 = '0', file_12 = '0', file_13 = '0', file_14 = '0', file_15 = '0', file_16 = '0', file_17 = '0', file_18 = '0', file_19 = '0', file_20 = '0', file_21 = '0', file_22 = '0', file_23 = '0', file_24 = '0', file_25 = '0', file_26 = '0', file_27 = '0', file_28 = '0', file_29 = '0', file_30 = '0' WHERE `IP` = '" . $ip . "' AND `HardwareID` = '" . $hardwareid . "' AND `HardwareID2` = '" . $hardwareid2 . "'");
			} else {
				$uConnect->query("INSERT INTO `AC_Players` (`Player`, `NextCheck`, `AppRunning`, `hasASI`, `hasTrainer`, `ConnectedIP`, `IP`, `HardwareID`, `HardwareID2`, `HardwareID3`, `HardwareID4`, `Volume`, `gpci`, `file_0`, `file_1`, `file_2`, `file_3`, `file_4`, `file_5`, `file_6`, `file_7`, `file_8`, `file_9`, `file_10`, `file_11`, `file_12`, `file_13`, `file_14`, `file_15`, `file_16`, `file_17`, `file_18`, `file_19`, `file_20`, `file_21`, `file_22`, `file_23`, `file_24`, `file_25`, `file_26`, `file_27`, `file_28`, `file_29`, `file_30`) VALUES ('" . $player . "', -1, 1, 'false', 'false', '" . $connectedip . "', '" . $ip . "', '" . $hardwareid . "', '" . $hardwareid2 . "', '" . $hardwareid3 . "', '" . $hardwareid4 . "', '" . $volume . "', '" . $gpci . "', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0')");
			}
			$result->free();
		}
			
		$uConnect->close();
		
		echo 'OK';
		exit();
	}
?>