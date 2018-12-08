import { Moment } from 'moment';
import { ICmRequest } from 'app/shared/model//cm-request.model';

export interface ICmAction {
    id?: number;
    actionNum?: number;
    actionType?: string;
    actionDescription?: string;
    actionInput?: string;
    actionOutput?: string;
    actionDateTime?: Moment;
    actionDuration?: number;
    request?: ICmRequest;
}

export class CmAction implements ICmAction {
    constructor(
        public id?: number,
        public actionNum?: number,
        public actionType?: string,
        public actionDescription?: string,
        public actionInput?: string,
        public actionOutput?: string,
        public actionDateTime?: Moment,
        public actionDuration?: number,
        public request?: ICmRequest
    ) {}
}
