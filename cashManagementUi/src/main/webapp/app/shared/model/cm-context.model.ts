import { Moment } from 'moment';
import { ICmRequest } from 'app/shared/model//cm-request.model';

export interface ICmContext {
    id?: number;
    contextId?: number;
    contextType?: number;
    contextName?: string;
    contextValue?: string;
    contextDateTime?: Moment;
    request?: ICmRequest;
}

export class CmContext implements ICmContext {
    constructor(
        public id?: number,
        public contextId?: number,
        public contextType?: number,
        public contextName?: string,
        public contextValue?: string,
        public contextDateTime?: Moment,
        public request?: ICmRequest
    ) {}
}
