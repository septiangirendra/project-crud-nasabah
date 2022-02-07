<?php
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Mendapatkan Nilai Variable
		$nama = $_POST['nama'];
		$no_rekening = $_POST['no_rekening'];
		$tabungan = $_POST['tabungan'];
		$saldo = $_POST['saldo'];
		
		//Pembuatan Syntax SQL
		$sql = "INSERT INTO tb_nasabah (nama,no_rekening,tabungan,saldo) 
		VALUES ('$nama','$no_rekening','$tabungan','$saldo')";
		
		//Import File Koneksi database
		require_once('koneksi.php');
		
		//Eksekusi Query database
		if(mysqli_query($con,$sql)){
			echo 'Berhasil Menambahkan Nasabah';
		}else{
			echo 'Gagal Menambahkan Nasabah';
		}
		
		mysqli_close($con);
	}
?>