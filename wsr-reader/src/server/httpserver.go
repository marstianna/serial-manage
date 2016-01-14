package server

import (
	"fmt"
	"io"
	"net/http"
	"errors"

	"wsr"
)

func Run(addr string) error {
	http.HandleFunc("/", defaultPage)
	http.HandleFunc("/read_card", readCard)
	if err := http.ListenAndServe(addr, nil); err != nil {
		return err
	}

	return nil
}

func defaultPage(w http.ResponseWriter, req *http.Request) {
	io.WriteString(w, newReplyFail(errors.New("wrong page")).toJsonString())
}

//读取卡号和uuid
func readCard(w http.ResponseWriter, req *http.Request) {
	wsr, err := wsr.NewWsr()
	if err != nil {
		fmt.Println("init failed, error:", err)
		io.WriteString(w, newReplyFail(err).toJsonString())
		return
	}
	defer func() {
		fmt.Println("destroying WSR")
		wsr.Destroy()
	}()

	if err := wsr.OpenPort(); err != nil {
		fmt.Println("open port failed, error:", err)
		io.WriteString(w, newReplyFail(err).toJsonString())
		return
	}
	defer func() {
		err := wsr.ClosePort()
		fmt.Println("close port, error:", err)
	}()

	if err := wsr.Beep(); err != nil {
		fmt.Println("beep failed, error:", err)
	}

	//read card no
	cardNo, err := wsr.GetCardNo()
	if err != nil {
		fmt.Println("getCardNo failed, error:", err)
		io.WriteString(w, newReplyFail(err).toJsonString())
		return
	}

	//read uuid
	uuid, err := wsr.ReadUuid()
	if err != nil {
		fmt.Println("readUuid failed, error:", err)
		io.WriteString(w, newReplyFail(err).toJsonString())
		return
	}

	succReply := newReplySucc()
	succReply.addData("cardno", cardNo)
	succReply.addData("uuid", uuid)

	io.WriteString(w, succReply.toJsonString())
}
