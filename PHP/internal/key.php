<?php
	require_once 'connect.php';

	function api_key_check() {
		if(strcmp($_POST['IP'], get_client_ip()) == 0 && strlen($_POST['IP']) > 0) {
			
			$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
				
			if($result = $uConnect->query("SELECT `MD5` FROM `AC_Info` WHERE `Type` = 'Client' LIMIT 1")) {
				
				$row = $result->fetch_assoc();
				
				$md5s = explode(",", $row['MD5']);
					
				for($i = 0; $i < count($md5s); $i++) {
					if((strcmp($_POST['APIKey'], $md5s[$i]) == 0) && strlen($_POST['APIKey']) > 0 && strlen($md5s[$i]) > 0) {
						$result->free();
						$ip = $uConnect->real_escape_string($_POST['IP']);
						$hardwareid = $uConnect->real_escape_string($_POST['HardwareID']);
						$hardwareid2 = $uConnect->real_escape_string($_POST['HardwareID2']);
						$volume = $uConnect->real_escape_string($_POST['Volume']);
							
						if($result_ = $uConnect->query("SELECT `Timestamp`, UNIX_TIMESTAMP() AS `unix_timestamp` FROM `AC_Bans` WHERE IP = '" . $ip . "' OR `HardwareID` = '" . $hardwareid . "' OR `HardwareID2` = '" . $hardwareid2 . "' OR `Volume` = '" . $volume . "' LIMIT 1")) {
							if($row = $result_->fetch_assoc()) {
								if($row['Timestamp'] != 0) {
									if($row['unix_timestamp'] > $row['Timestamp']) {
										$result_->free();
										$uConnect->close();
										return 1;
									} else {
										$result_->free();
										$uConnect->close();
										return 0;
									}
								} else {
									$result_->free();
									$uConnect->close();
									return 0;
								}								
							} else {
								$result_->free();
								$uConnect->close();
								return 1;
							}
							$result_->free();
						}
						$uConnect->close();
						return 1;
					}
				}			
				echo 'api keys not matching.';
			}
			
			$uConnect->close();
		} else {
			echo 'key failed due to ip -> ' . get_client_ip();
		}
		return 0;
	} 
	
	function echo_error() {
		echo 'No.';
	}
	
	function get_client_ip() {
		$ipaddress = '';
		if($_SERVER['REMOTE_ADDR'])
			$ipaddress = $_SERVER['REMOTE_ADDR'];
		else
			$ipaddress = 'UNKNOWN';
	 
		return $ipaddress;
	}
	
	
	if(api_key_check() == 1) begin();
	else { echo_error(); }
?>