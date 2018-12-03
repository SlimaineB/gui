import { Moment } from 'moment';
import { ICmAction } from 'app/shared/model//cm-action.model';
import { ICmContext } from 'app/shared/model//cm-context.model';
import { ICmError } from 'app/shared/model//cm-error.model';

export interface ICmRequest {
    id?: number;
    requestId?: number;
    requestUuid?: string;
    serviceName?: string;
    serviceEndpoint?: string;
    instanceHostname?: Moment;
    instancePort?: number;
    requestBody?: string;
    requestHeader?: string;
    responseBody?: string;
    responseHeader?: string;
    returnHttpCode?: number;
    technicalStatus?: string;
    functionalStatus?: string;
    startDateTime?: Moment;
    endDateTime?: Moment;
    requestDuration?: number;
    actions?: ICmAction[];
    contexts?: ICmContext[];
    errors?: ICmError[];
}

export class CmRequest implements ICmRequest {
    constructor(
        public id?: number,
        public requestId?: number,
        public requestUuid?: string,
        public serviceName?: string,
        public serviceEndpoint?: string,
        public instanceHostname?: Moment,
        public instancePort?: number,
        public requestBody?: string,
        public requestHeader?: string,
        public responseBody?: string,
        public responseHeader?: string,
        public returnHttpCode?: number,
        public technicalStatus?: string,
        public functionalStatus?: string,
        public startDateTime?: Moment,
        public endDateTime?: Moment,
        public requestDuration?: number,
        public actions?: ICmAction[],
        public contexts?: ICmContext[],
        public errors?: ICmError[]
    ) {}
}
