"use strict";

function fiveFunction(k) {
    return function (l, r, t, h, g) {
        return function () {
            return k(l.apply(null, arguments), r.apply(null, arguments), t.apply(null, arguments),
                h.apply(null, arguments), g.apply(null, arguments));
        }
    }
}

function tripleFunction(k) {
    return function (l, r, t) {
        return function () {
            return k(l.apply(null, arguments), r.apply(null, arguments), t.apply(null, arguments));
        }
    }
}

function binaryFunction(k) {
    return function (l, r) {
        return function () {
            return k(l.apply(null, arguments), r.apply(null, arguments));
        }
    }
}

function unaryFunction(k) {
    return function (l) {
        return function () {
            return k(l.apply(null, arguments));
        }
    }
}

var arr = ['x', 'y', 'z'];

var add = binaryFunction(function (l, r) {return l + r;});

var min3 = tripleFunction(function (l, r, t) {
    return Math.min(l, r, t);
});

var max5 = fiveFunction(function (l, r, t, h, g) {
    return Math.max(l, r, t, h, g);
});

var subtract = binaryFunction(function (l, r) {
    return l - r;
});

var pi = function () {
    return Math.PI;
};

var x = function () {
    return arguments[0];
};

var y = function () {
    return arguments[1];
};

var z = function () {
    return arguments[2];
};

var e = function () {
    return Math.E;
};

var multiply = binaryFunction(function (l, r) {
    return l * r;
});

var divide = binaryFunction(function (l, r) {
    return l / r;
});

var negate = unaryFunction(function (l) {
    return l * -1;
});

var cnst = function (n) {
    return function () {
        return n;
    };
};

var variable = function (n) {
    return function () {
        for (var i = 0; i < arr.length; i++) {
            if (n === arr[i]) {
                return arguments[i];
            }
        }
    }
};

var par = function (f) {
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

var parse = par(function () {
    var numbers = [];
    var inputVariables = [];
    var input = arguments[0].split(" ");

    for (var j = 0; j < arr.length; j++) {
        inputVariables.push(arguments[j + 1]);
    }
    for (var i = 0; i < input.length; i++) {
        if(input[i].length === 0) {
            continue;
        }
        if (input[i] === "-" || input[i] === "+" || input[i] === "*" || input[i] === "/") {
            eval("numbers[numbers.length - 2] = numbers[numbers.length - 2]" + input[i] + "numbers.pop()");
            continue;
        }

        if (input[i] === "negate") {
            numbers[numbers.length - 1] *= (-1);
            continue;
        }

        if (input[i] === "pi") {
            numbers.push(Math.PI);
            continue;
        }

        if (input[i] === "e") {
            numbers.push(Math.E);
            continue;
        }

        if (input[i] === "min3") {
            numbers[numbers.length - 3] = Math.min(numbers[numbers.length - 3], numbers.pop(), numbers.pop());
            continue;
        }

        if (input[i] === "max5") {
            numbers[numbers.length - 5] = Math.max(numbers[numbers.length - 5], numbers.pop(), numbers.pop(),
                numbers.pop(), numbers.pop());
            continue;
        }

        if(input[i] === "x" || input[i] === "y" || input[i] === "z") {
            numbers.push(inputVariables[arr.indexOf(input[i])]);
            continue;
        }

        numbers.push(parseInt(input[i]));
    }
    return numbers[0];
});