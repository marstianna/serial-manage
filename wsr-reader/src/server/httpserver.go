package server

import (
	"fmt"
	"io"
	"net/http"

	"wsr"
)

func Run(addr string) error {
	http.HandleFunc("/test", test)
	http.HandleFunc("/read_card", readCard)
	if err := http.ListenAndServe(addr, nil); err != nil {
		return err
	}

	return nil
}

func test(w http.ResponseWriter, req *http.Request) {

}

//读取卡号和uuid
func readCard(w http.ResponseWriter, req *http.Request) {
	rep := NewHttpResult()
	wsr, code, err := wsr.NewWsr()
	if err != nil {
		fmt.Println("init failed, error:", err)
		rep.setCodeAndStatus(code)
		io.WriteString(w, rep.toJsonString())
		return
	}
	defer func() {
		fmt.Println("destroying WSR")
		wsr.Destroy()
	}()

	if code, err := wsr.OpenPort(); err != nil {
		fmt.Println("open port failed, error:", err)
		rep.setCodeAndStatus(code)
		io.WriteString(w, rep.toJsonString())
		return
	}
	defer func() {
		_, err := wsr.ClosePort()
		fmt.Println("close port, error:", err)
	}()

	if _, err := wsr.Beep(); err != nil {
		fmt.Println("beep failed, error:", err)
	}

	//read card no
	cardNo, code, err := wsr.GetCardNo()
	if err != nil {
		fmt.Println("getCardNo failed, error:", err)
		rep.setCodeAndStatus(code)
		io.WriteString(w, rep.toJsonString())
		return
	}

	//read uuid
	uuid, code, err := wsr.ReadUuid()
	if err != nil {
		fmt.Println("readUuid failed, error:", err)
		rep.setCodeAndStatus(code)
		io.WriteString(w, rep.toJsonString())
		return
	}

	fmt.Println("cardNo:", cardNo, ", uuid:", uuid)
	rep.addData("cardno", cardNo)
	rep.addData("uuid", uuid)

	io.WriteString(w, rep.toJsonString())
}
