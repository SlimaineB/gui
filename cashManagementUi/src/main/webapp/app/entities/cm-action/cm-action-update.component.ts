import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICmAction } from 'app/shared/model/cm-action.model';
import { CmActionService } from './cm-action.service';
import { ICmRequest } from 'app/shared/model/cm-request.model';
import { CmRequestService } from 'app/entities/cm-request';

@Component({
    selector: 'jhi-cm-action-update',
    templateUrl: './cm-action-update.component.html'
})
export class CmActionUpdateComponent implements OnInit {
    cmAction: ICmAction;
    isSaving: boolean;

    cmrequests: ICmRequest[];
    actionDateTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private cmActionService: CmActionService,
        private cmRequestService: CmRequestService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cmAction }) => {
            this.cmAction = cmAction;
            this.actionDateTime = this.cmAction.actionDateTime != null ? this.cmAction.actionDateTime.format(DATE_TIME_FORMAT) : null;
        });
        this.cmRequestService.query().subscribe(
            (res: HttpResponse<ICmRequest[]>) => {
                this.cmrequests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cmAction.actionDateTime = this.actionDateTime != null ? moment(this.actionDateTime, DATE_TIME_FORMAT) : null;
        if (this.cmAction.id !== undefined) {
            this.subscribeToSaveResponse(this.cmActionService.update(this.cmAction));
        } else {
            this.subscribeToSaveResponse(this.cmActionService.create(this.cmAction));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICmAction>>) {
        result.subscribe((res: HttpResponse<ICmAction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCmRequestById(index: number, item: ICmRequest) {
        return item.id;
    }
}
