export interface ICallLogAction {
    id?: number;
    callLogActionId?: number;
    callLogActionDescription?: string;
}

export class CallLogAction implements ICallLogAction {
    constructor(public id?: number, public callLogActionId?: number, public callLogActionDescription?: string) {}
}
