export interface IProspect {
    id?: number;
    prospectId?: number;
    prospectName?: string;
}

export class Prospect implements IProspect {
    constructor(public id?: number, public prospectId?: number, public prospectName?: string) {}
}
