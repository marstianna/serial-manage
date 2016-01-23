package server

import (
	"fmt"
	"io"
	"net/http"

	"wsr-reader/wsr"
)

func Run(addr string) error {
	http.HandleFunc("/", defaultPage)
	http.HandleFunc("/read_card", readCard)
	http.HandleFunc("/write_card", writeCard)
	if err := http.ListenAndServe(addr, nil); err != nil {
		return err
	}

	return nil
}

func defaultPage(w http.ResponseWriter, req *http.Request) {
	io.WriteString(w, newReplyFailWithString("wrong page").toJsonString())
}

//读取卡号和uuid
func readCard(w http.ResponseWriter, req *http.Request) {
	fmt.Println("\r\n收到读卡请求")
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

	//load password
	if err := wsr.LoadPsw("\xFF\xFF\xFF\xFF\xFF\xFF", 0); err != nil {
		fmt.Println("load passwd failed, error:", err)
		io.WriteString(w, newReplyFail(err).toJsonString())
		return
	}
	fmt.Println("load passwd succ")

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

func writeCard(w http.ResponseWriter, req *http.Request) {
	fmt.Println("\r\n收到写卡请求")

	req.ParseForm()
	if req.Method != "POST" {
		io.WriteString(w, newReplyFailWithString("请求方法错误").toJsonString())
		return
	}

	uuidList := req.PostForm["uuid"]
	if len(uuidList) != 1 || len(uuidList[0]) == 0 {
		io.WriteString(w, newReplyFailWithString("请求参数错误").toJsonString())
		return
	}

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

	//load password
	if err := wsr.LoadPsw("\xFF\xFF\xFF\xFF\xFF\xFF", 0); err != nil {
		fmt.Println("load passwd failed, error:", err)
		io.WriteString(w, newReplyFail(err).toJsonString())
		return
	}
	fmt.Println("load passwd succ")

	if err := wsr.WriteUuid(uuidList[0]); err != nil {
		fmt.Println("write uuid failed, err:", err)
		io.WriteString(w, newReplyFail(err).toJsonString())
		return
	}

	io.WriteString(w, newReplySucc().toJsonString())
}
