<?php
	
	require_once 'key.php';
	require_once 'connect.php';
		
	function begin() {
		
		$uConnect = new mysqli(mysql_host, mysql_user, mysql_pw, mysql_db);
		
		if($result = $uConnect->query("SELECT `hash` FROM `AC_Hashes` WHERE `filename` = 'samp.exe' OR `filename` = 'samp.dll' OR `filename` = 'samp.saa' ORDER BY `ID` ASC LIMIT 3")) {
			$fullres = '';
			while($row = $result->fetch_assoc()) {
				if(!strlen($fullres)) {
					$fullres = $row['hash'];
				} else {
					$fullres = $fullres . ' ' . $row['hash'];
				}
			}	
			
			if(!strlen($fullres)) {
				echo 'Unknown';
				$result->free();
				$uConnect->close();
				return 1;
			}
			echo $fullres;
			$result->free();
		}
		$uConnect->close();
		exit();
	}
?>
