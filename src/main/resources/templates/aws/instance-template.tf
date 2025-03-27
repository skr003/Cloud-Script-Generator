resource "aws_instance" "example" {
  ami           = "{{ami}}"
  instance_type = "{{type}}"
}
