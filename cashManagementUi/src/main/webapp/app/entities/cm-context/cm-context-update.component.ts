import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICmContext } from 'app/shared/model/cm-context.model';
import { CmContextService } from './cm-context.service';
import { ICmRequest } from 'app/shared/model/cm-request.model';
import { CmRequestService } from 'app/entities/cm-request';

@Component({
    selector: 'jhi-cm-context-update',
    templateUrl: './cm-context-update.component.html'
})
export class CmContextUpdateComponent implements OnInit {
    cmContext: ICmContext;
    isSaving: boolean;

    cmrequests: ICmRequest[];
    contextDateTime: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private cmContextService: CmContextService,
        private cmRequestService: CmRequestService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cmContext }) => {
            this.cmContext = cmContext;
            this.contextDateTime = this.cmContext.contextDateTime != null ? this.cmContext.contextDateTime.format(DATE_TIME_FORMAT) : null;
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
        this.cmContext.contextDateTime = this.contextDateTime != null ? moment(this.contextDateTime, DATE_TIME_FORMAT) : null;
        if (this.cmContext.id !== undefined) {
            this.subscribeToSaveResponse(this.cmContextService.update(this.cmContext));
        } else {
            this.subscribeToSaveResponse(this.cmContextService.create(this.cmContext));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICmContext>>) {
        result.subscribe((res: HttpResponse<ICmContext>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
