package main

import (
	"fmt"
	"server"
)

func main() {
	if err := server.Run(":8088"); err != nil {
		fmt.Println("server failed to start, error:", err)
	}

	//	wsr, err := wsr.NewWsr()
	//	if err != nil {
	//		fmt.Println("init failed, error:", err)
	//		return
	//	}
	//	defer func() {
	//		fmt.Println("destroying WSR")
	//		wsr.Destroy()
	//	}()
	//
	//	if err := wsr.OpenPort(); err != nil {
	//		fmt.Println("open port failed, error:", err)
	//		return
	//	}
	//	defer func() {
	//		err := wsr.ClosePort()
	//		fmt.Println("close port, error:", err)
	//	}()
	//
	//	if err := wsr.Beep(); err != nil {
	//		fmt.Println("beep failed, error:", err)
	//	}
	//
	//	//read card no
	//	cardNo, err := wsr.GetCardNo()
	//	if err != nil {
	//		fmt.Println("getCardNo failed, error:", err)
	//		return
	//	}
	//	fmt.Println("cardNo:", cardNo)
	//
	//	//load password
	//	if err := wsr.LoadPsw("\xFF\xFF\xFF\xFF\xFF\xFF", 0); err != nil {
	//		fmt.Println("load passwd failed, error:", err)
	//		return
	//	}
	//	fmt.Println("load passwd succ")
	//
	//	//write uuid
	//	//	if err := wsr.WriteUuid("1234567890123456789012345678901234567890"); err != nil {
	//	//		fmt.Println("write uuid failed, error:", err)
	//	//		return
	//	//	}
	//
	//	//set passwd
	//	if err := wsr.SetPsw("123456", "abcd", "654321"); err != nil {
	//		fmt.Println("set passwd failed, error:", err)
	//		return
	//	}
	//	fmt.Println("set passwd succ")
	//
	//	//read uuid
	//	uuid, err := wsr.ReadUuid()
	//	if err != nil {
	//		fmt.Println("read uuid failed, error:", err)
	//		return
	//	}
	//	fmt.Println("uuid:", uuid)
}
