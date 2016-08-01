<?php
	
	//require_once 'key.php';
	require_once 'connect.php';
		
	function begin() {

		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		$ip = $uConnect->real_escape_string($_POST['BannedIP']);
		$hardwareid = $uConnect->real_escape_string($_POST['HardwareID']);
		$hardwareid2 = $uConnect->real_escape_string($_POST['HardwareID2']);
		$hardwareid3 = "INVALID HARDWAREID 3";
		$hardwareid4 = "INVALID HARDWAREID 4";
		$volume = $uConnect->real_escape_string($_POST['Volume']);
		
		if($result = $uConnect->query("SELECT `ID`, `IP`, `HardwareID`, `HardwareID2`, `HardwareID3`, `HardwareID4`, `Volume`, `session`, `Evaded`, `Name`, `IgnoredNames`, `Timestamp`, `Reason`, UNIX_TIMESTAMP() AS `unix_timestamp` FROM `AC_Bans` WHERE IP = '" . $ip . "' OR `HardwareID` = '" . $hardwareid . "' OR `HardwareID2` = '" . $hardwareid2 . "' OR `Volume` = '" . $volume . "' ORDER BY `TimeAdded` DESC ")) {
			while($row = $result->fetch_assoc()) {
				if($row['Timestamp'] != 0) {
					if($row['unix_timestamp'] > $row['Timestamp']) {
						echo 'Not banned.';
						$result->free();
						$uConnect->close();
						exit();
						return 1;
						break;
					} else {
						if(strcmp($row['HardwareID'], $hardwareid) != 0 || strcmp($row['HardwareID2'], $hardwareid2) != 0 || strcmp($row['Volume'], $volume) != 0) {
							$uConnect->query("INSERT INTO `AC_Bans` (`HardwareID`, `HardwareID2`, `HardwareID3`, `HardwareID4`, `Volume`, `session`, `Evaded`, `IP`, `Name`, `Reason`, `IgnoredNames`, `Timestamp`) VALUES ('" . $hardwareid . "', '" . $hardwareid2 . "', '" . $hardwareid3 . "', '" . $hardwareid4 . "', '" . $volume . "', '" . $row['session'] . "', " . $row['ID'] . ", '" . get_client_ip() . "', '" . $name . "', '" . $row['Reason'] . "', '" . $row['IgnoredNames'] . "', " . $row['Timestamp'] . ")");
						}
						
						echo '' . $row['Name'] . ' ' . $row['Timestamp'] . ' ' . $row['Reason']; 
						$result->free();
						$uConnect->close();
						exit();
						return 1;
						break;
					}
				} else {
					if(strcmp($row['HardwareID'], $hardwareid) != 0 || strcmp($row['HardwareID2'], $hardwareid2) != 0 || strcmp($row['Volume'], $volume) != 0) {
						$uConnect->query("INSERT INTO `AC_Bans` (`HardwareID`, `HardwareID2`, `HardwareID3`, `HardwareID4`, `Volume`, `session`, `Evaded`, `IP`, `Name`, `Reason`, `IgnoredNames`, `Timestamp`) VALUES ('" . $hardwareid . "', '" . $hardwareid2 . "', '" . $hardwareid3 . "', '" . $hardwareid4 . "', '" . $volume . "', '" . $row['session'] . "', " . $row['ID'] . ", '" . get_client_ip() . "', '" . $name . "', '" . $row['Reason'] . "', '" . $row['IgnoredNames'] . "', " . $row['Timestamp'] . ")");
					}
					echo '' . $row['Name'] . ' ' . $row['Timestamp'] . ' ' . $row['Reason']; 
					$result->free();
					$uConnect->close();
					exit();
					return 1;
					break;
				}
			} 
		
			echo 'Not banned.';
			$result->free();
		}
		$uConnect->close();
		exit();
	}

	function get_client_ip() {
		$ipaddress = '';
		if($_SERVER['REMOTE_ADDR'])
			$ipaddress = $_SERVER['REMOTE_ADDR'];
		else
			$ipaddress = 'UNKNOWN';
	 
		return $ipaddress;
	}
	
	begin();
	
?>
