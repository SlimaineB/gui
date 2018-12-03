import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICmRequest } from 'app/shared/model/cm-request.model';
import { CmRequestService } from './cm-request.service';

@Component({
    selector: 'jhi-cm-request-delete-dialog',
    templateUrl: './cm-request-delete-dialog.component.html'
})
export class CmRequestDeleteDialogComponent {
    cmRequest: ICmRequest;

    constructor(private cmRequestService: CmRequestService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.cmRequestService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'cmRequestListModification',
                content: 'Deleted an cmRequest'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cm-request-delete-popup',
    template: ''
})
export class CmRequestDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cmRequest }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CmRequestDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.cmRequest = cmRequest;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
