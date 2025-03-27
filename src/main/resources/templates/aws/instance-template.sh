#!/bin/bash
aws ec2 run-instances --image-id {{ami}} --count 1 --instance-type {{type}}
