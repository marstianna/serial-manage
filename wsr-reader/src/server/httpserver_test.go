package server

import (
	"testing"
)

func TestServer(t *testing.T) {
	if err := Run(":12345"); err != nil {
		t.Error("error")
	}
}