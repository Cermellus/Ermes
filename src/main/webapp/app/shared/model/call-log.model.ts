import { Moment } from 'moment';

export interface ICallLog {
    id?: number;
    callLogId?: number;
    callLogComment?: string;
    callLogDate?: Moment;
    callLogContactMail?: string;
    customerIdId?: number;
    prospectIdId?: number;
    appUserIdId?: number;
}

export class CallLog implements ICallLog {
    constructor(
        public id?: number,
        public callLogId?: number,
        public callLogComment?: string,
        public callLogDate?: Moment,
        public callLogContactMail?: string,
        public customerIdId?: number,
        public prospectIdId?: number,
        public appUserIdId?: number
    ) {}
}
