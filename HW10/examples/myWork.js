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

Abstract.prototype.toString = function (a) {
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

function Sinh(x) {
    Abstract.call(this, x);
    this.operation = function (a) {
        return (Math.pow(Math.E, a) - Math.pow(Math.E, -1 * a)) / 2;
    };
    this.string = this.stringBuilder(x, "sinh");
}

function Cosh(x) {
    Abstract.call(this, x);
    this.operation = function (a) {
        return (Math.pow(Math.E, a) + Math.pow(Math.E, -1 * a)) / 2;
    };
    this.string = this.stringBuilder(x, "cosh");
}

var ptr = [Negate, Add, Const, Variable, Subtract, Multiply, Divide, Sinh, Cosh];
for (var i = 0; i < ptr.length; i++) {
    ptr[i].prototype = Object.create(Abstract.prototype);
}

var expr = new Add(new Variable('x'), new Const(2));
println(expr.string);