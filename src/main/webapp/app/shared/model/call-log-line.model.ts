import { Moment } from 'moment';

export interface ICallLogLine {
    id?: number;
    callLogLineId?: number;
    callLogComment?: string;
    callLogDueDate?: Moment;
    productIdId?: number;
    callLogActionIdId?: number;
    callLogIdId?: number;
}

export class CallLogLine implements ICallLogLine {
    constructor(
        public id?: number,
        public callLogLineId?: number,
        public callLogComment?: string,
        public callLogDueDate?: Moment,
        public productIdId?: number,
        public callLogActionIdId?: number,
        public callLogIdId?: number
    ) {}
}
