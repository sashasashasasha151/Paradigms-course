"use strict";

function Abstract() {
    this.ar = arguments;
    this.stringBuilder = function () {
        var string = "";
        for (var i = 0; i < arguments.length - 1; i++) {
            string += arguments[i].toString() + " ";
        }
        return string + arguments[i];
    }
}

Abstract.prototype.evaluate = function () {
    if (this.ar.length === 2) {
        return this.operation(this.ar[0].evaluate.apply(this.ar[0], arguments), this.ar[1].evaluate.apply(this.ar[1], arguments));
    }
    if (this.ar.length === 1) {
        return this.operation(this.ar[0].evaluate.apply(this.ar[0], arguments));
    }
    if (this.ar.length === 0) {
        return this.operation(arguments);
    }
};

Abstract.prototype.diff = function (x) {
    return 0;
};

function parse(x) {
    return 10;
}

Abstract.prototype.toString = function () {
    return this.string;
};

var arr = ['x', 'y', 'z'];

function Const(x) {
    Abstract.call(this);
    this.operation = function () {
        return x;
    };
    this.string = this.stringBuilder(x);
}

function Variable(x) {
    Abstract.call(this);
    this.operation = function () {
        return arguments[0][arr.indexOf(x)];
    };
    this.string = this.stringBuilder(x);
}

function Add(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return a + b;
    };
    this.string = this.stringBuilder(x, y, "+");
    this.diff = function (arg) {
        return new Add(x.diff(arg),y.diff(arg))
    }
}

function Subtract(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return a - b;
    };
    this.string = this.stringBuilder(x, y, "-");
}

function Multiply(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return a * b;
    };
    this.string = this.stringBuilder(x, y, "*");
}

function Divide(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return a / b;
    };
    this.string = this.stringBuilder(x, y, "/");
}

function Negate(x) {
    Abstract.call(this, x);
    this.operation = function (a) {
        return -1 * a;
    };
    this.string = this.stringBuilder(x, "negate");
}

function Power(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return Math.pow(a,b);
    };
    this.string = this.stringBuilder(x, y, "pow");
}

function Log(x, y) {
    Abstract.call(this, x, y);
    this.operation = function (a, b) {
        return Math.log(Math.abs(b))/ Math.log(Math.abs(a));
    };
    this.string = this.stringBuilder(x, y, "log");
}

var ptr = [Negate, Add, Const, Variable, Subtract, Multiply, Divide, Power, Log];
for (var i = 0; i < ptr.length; i++) {
    ptr[i].prototype = Object.create(Abstract.prototype);
}