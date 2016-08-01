<?php
	function updatedb($uConnect, $ip) {
		$uConnect->query("UPDATE `AC_Players` SET `NextCheck` = UNIX_TIMESTAMP() + 80, `AppRunning` = 1 WHERE `IP` = '" . $ip . "'");
	}
?>