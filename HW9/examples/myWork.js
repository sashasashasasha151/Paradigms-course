"use strict";

var mCurry = function (f) {
    return function (a) {
        return function () {
            var newArguments = [a];
            for (var i = 0; i < arguments.length; i++) {
                newArguments.push(arguments[i]);
            }
            return f.apply(null, newArguments);
        }
    }
};

var arr = ['x', 'y', 'z', 'u', 'v', 'w'];
var parse = mCurry(function () {
    var string = arguments[0];
    var numbers = [];
    var inputVariables = [];
    for (var j = 0; j < arr.length; j++) {
        inputVariables.push(arguments[j+1]);
    }
    for(var i = 0; i < string.length; i++) {
        if(string[i] === ' ') {
            continue;
        }
        if(string[i]==='n') {
            i+=6;
            while(string[i] === ' ') {
                i++;
            }
            var left = i++;
            while (!isNaN(parseInt(string[i]))) {
                i++;
            }
            numbers.push(parseInt(string.substring(left,i)));
            i--;
            continue;

        }
        if(!isNaN(parseInt(string[i])) ) {
            var left = i++;
            while (!isNaN(parseInt(string[i]))) {
                i++;
            }
            numbers.push(parseInt(string.substring(left,i)));
            i--;
            continue;
        }
        if(string[i] === '+' || string[i] === '-' || string[i] === '*' || string[i] === '/') {
            var op = string[i];
            switch (op) {
                case '+':
                    numbers[numbers.length-2] = numbers[numbers.length-2] + numbers.pop();
                    break;
                case '-':
                    numbers[numbers.length-2] = numbers[numbers.length-2] - numbers.pop();
                    break;
                case '*':
                    numbers[numbers.length-2] = numbers[numbers.length-2] * numbers.pop();
                    break;
                case '/':
                    numbers[numbers.length-2] = numbers[numbers.length-2] / numbers.pop();
                    break;
            }
            continue;
        }
        switch (string[i]) {
            case 'x':
                numbers.push(inputVariables[0]);
                break;
            case 'y':
                numbers.push(inputVariables[1]);
                break;
            case 'z':
                numbers.push(inputVariables[2]);
                break;
            case 'u':
                numbers.push(inputVariables[3]);
                break;
            case 'v':
                numbers.push(inputVariables[4]);
                break;
            case 'w':
                numbers.push(inputVariables[5]);
                break;
        }
    }
    return numbers[0];
});
println(parse("x negate 2 /")(1,2,3,4,5,6));